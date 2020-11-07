# 使用FastDFS搭建图片服务器（单机版）

## 一、FastDFS介绍

FastDFS开源地址：https://github.com/happyfish100

参考：https://www.cnblogs.com/huiyi0521/p/10255848.html

### 1、简介

FastDFS 是一个开源的高性能分布式文件系统（DFS）。 它的主要功能包括：文件存储，文件同步和文件访问，以及高容量和负载平衡。主要解决了海量数据存储问题，特别适合以中小文件（建议范围：4KB < file_size <500MB）为载体的在线服务。

FastDFS 系统有三个角色：跟踪服务器(Tracker Server)、存储服务器(Storage Server)和客户端(Client)。

**Tracker Server**：跟踪服务器，主要做调度工作，起到均衡的作用；负责管理所有的 storage server和 group，每个 storage 在启动后会连接 Tracker，告知自己所属 group 等信息，并保持周期性心跳。

**Storage Server**：存储服务器，主要提供容量和备份服务；以 group 为单位，每个 group 内可以有多台 storage server，数据互为备份。

**Client**：客户端，上传下载数据的服务器，也就是我们自己的项目所部署在的服务器。

 ![img](https://images2017.cnblogs.com/blog/856154/201710/856154-20171011144153840-1185141903.png)

### **2、FastDFS的存储策略**

为了支持大容量，存储节点（服务器）采用了分卷（或分组）的组织方式。存储系统由一个或多个卷组成，卷与卷之间的文件是相互独立的，所有卷的文件容量累加就是整个存储系统中的文件容量。一个卷可以由一台或多台存储服务器组成，一个卷下的存储服务器中的文件都是相同的，卷中的多台存储服务器起到了冗余备份和负载均衡的作用。

在卷中增加服务器时，同步已有的文件由系统自动完成，同步完成后，系统自动将新增服务器切换到线上提供服务。当存储空间不足或即将耗尽时，可以动态添加卷。只需要增加一台或多台服务器，并将它们配置为一个新的卷，这样就扩大了存储系统的容量。

 

### **3、FastDFS的上传过程**

FastDFS向使用者提供基本文件访问接口，比如upload、download、append、delete等，以客户端库的方式提供给用户使用。

Storage Server会定期的向Tracker Server发送自己的存储信息。当Tracker Server Cluster中的Tracker Server不止一个时，各个Tracker之间的关系是对等的，所以客户端上传时可以选择任意一个Tracker。

当Tracker收到客户端上传文件的请求时，会为该文件分配一个可以存储文件的group，当选定了group后就要决定给客户端分配group中的哪一个storage server。当分配好storage server后，客户端向storage发送写文件请求，storage将会为文件分配一个数据存储目录。然后为文件分配一个fileid，最后根据以上的信息生成文件名存储文件。

![img](https://images2017.cnblogs.com/blog/856154/201710/856154-20171012121639387-1574147926.png)

### **4、FastDFS的文件同步**

写文件时，客户端将文件写至group内一个storage server即认为写文件成功，storage server写完文件后，会由后台线程将文件同步至同group内其他的storage server。

每个storage写文件后，同时会写一份binlog，binlog里不包含文件数据，只包含文件名等元信息，这份binlog用于后台同步，storage会记录向group内其他storage同步的进度，以便重启后能接上次的进度继续同步；进度以时间戳的方式进行记录，所以最好能保证集群内所有server的时钟保持同步。

storage的同步进度会作为元数据的一部分汇报到tracker上，tracke在选择读storage的时候会以同步进度作为参考。

### **5、FastDFS的文件下载**

客户端uploadfile成功后，会拿到一个storage生成的文件名，接下来客户端根据这个文件名即可访问到该文件。

![img](https://images2015.cnblogs.com/blog/380252/201704/380252-20170415090611017-204910775.png)

跟upload file一样，在downloadfile时客户端可以选择任意tracker server。tracker发送download请求给某个tracker，必须带上文件名信息，tracke从文件名中解析出文件的group、大小、创建时间等信息，然后为该请求选择一个storage用来服务读请求。

 

## 二、安装FastDFS环境

### 0、前言

操作环境：CentOS7 X64，以下操作都是单机环境。

首次配置编写,如有不对地方欢迎指正!,博主第一次配置,参考网上资料,一步步解决难题,最终成功了,如果你也是第一次不妨试试我的

我把所有的安装包下载到/tools/下，解压到当前目录。

先做一件事，修改hosts，将文件服务器的ip与域名映射(单机TrackerServer环境)，因为后面很多配置里面都需要去配置服务器地址，ip变了，就只需要修改hosts即可。

```
 vim /etc/hosts

增加如下一行，这是我的IP
10.0.0.5 images.lulu.com

如果要本机访问虚拟机，在C:\Windows\System32\drivers\etc\hosts中同样增加一行
10.0.0.5 images.lulu.com
```

Linux:

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111120400928-106020672.png)

 

Windows:

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111115349843-2139021109.png)

 

以上ip地址必须是你的linux真实ip, 域名可以自定义,你开心,定义中文名都可以

### 1、下载安装 libfastcommon

libfastcommon是从 FastDFS 和 FastDHT 中提取出来的公共 C 函数库，基础环境，安装即可 。

#### 1.1下载libfastcommon

```
wget https://github.com/happyfish100/libfastcommon/archive/V1.0.7.tar.gz
```

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111022116141-1389868524.png)

 

#### 1.2 解压

```
tar -zxf V1.0.7.tar.gz
cd libfastcommon-1.0.7
```

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111022248414-1819675227.png)

 

#### 1.3:编译、安装

```
./make.sh && ./make.sh install
```

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111021819772-1228707255.png)

 

libfastcommon.so 安装到了/usr/lib64/libfastcommon.so，但是FastDFS主程序设置的lib目录是/usr/local/lib，所以需要创建软链接。

```
ln -s /usr/lib64/libfastcommon.so /usr/local/lib/libfastcommon.so
ln -s /usr/lib64/libfastcommon.so /usr/lib/libfastcommon.so
ln -s /usr/lib64/libfdfsclient.so /usr/local/lib/libfdfsclient.so
ln -s /usr/lib64/libfdfsclient.so /usr/lib/libfdfsclient.so 
```

 ![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111021926938-1493725671.png)

 

### 2、下载安装FastDFS

#### 2.1: 下载FastDFS

```
wget https://github.com/happyfish100/fastdfs/archive/V5.05.tar.gz
```

 ![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111022602532-1096563650.png)

 

####  2.2:解压

```
tar -zxf V5.05.tar.gz
cd fastdfs-5.05
```

 

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111022930842-664716434.png)

 

#### 2.3: 编译、安装

```
./make.sh && ./make.sh install
```

如果编译报错,可能是缺少相关依赖,我也不知道你缺啥,来吧,下面命令执行一遍

```
yum -y install tree zlib zlib-devel pcre pcre-devel gcc gcc-c++ openssl openssl-devel libevent libevent-devel perl unzip net-tools wget
```

 

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111023209761-557555177.png)

......没有报错!

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111023305462-839850244.png)

安装ok

#### 2.4: 默认安装方式安装后的相应文件与目录

##### 2.4.1:服务脚本：

```
/etc/init.d/fdfs_storaged
/etc/init.d/fdfs_tracker
```

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111024434975-248886856.png)

 

##### 2.4.2、配置文件

（这三个是作者给的样例配置文件） :

```
/etc/fdfs/client.conf.sample
/etc/fdfs/storage.conf.sample
/etc/fdfs/tracker.conf.sample
```

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111024552739-1464055451.png)

 

### 　　2.4.3、命令工具

　　在 /usr/bin/ 目录下：

```
fdfs_appender_test
fdfs_appender_test1
fdfs_append_file
fdfs_crc32
fdfs_delete_file
fdfs_download_file
fdfs_file_info
fdfs_monitor
fdfs_storaged
fdfs_test
fdfs_test1
fdfs_trackerd
fdfs_upload_appender
fdfs_upload_file
stop.sh
restart.sh
```

 ![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111024944146-1489778281.png)

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111025202884-2082182194.png)

 

####  2.5: FastDFS 服务脚本设置

　　 bin 目录是 /usr/local/bin， 但实际命令安装在 /usr/bin/ 下。

　　建立 /usr/bin 到 /usr/local/bin 的软链接

```
ln -s /usr/bin/fdfs_trackerd   /usr/local/bin
ln -s /usr/bin/fdfs_storaged   /usr/local/bin
ln -s /usr/bin/stop.sh         /usr/local/bin
ln -s /usr/bin/restart.sh      /usr/local/bin
```

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111105013107-1062719713.png)

### 3、配置FastDFS跟踪器(Tracker)

####  3.1: 创建配置文件

　　进入 /etc/fdfs，复制 FastDFS 跟踪器样例配置文件 tracker.conf.sample，并重命名为 tracker.conf。

```
cd /etc/fdfs
cp tracker.conf.sample tracker.conf
vim tracker.conf
```

#### 3.2: 编辑配置Tracker

　　　　编辑tracker.conf ，标红的需要修改下，其它的默认即可。

```
# 配置文件是否不生效，false 为生效
disabled=false

# 提供服务的端口
port=22122

# Tracker 数据和日志目录地址(根目录必须存在,子目录会自动创建)
base_path=/lulu/fastdfs/tracker

# HTTP 服务端口
http.server_port=80
```

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111103859362-1471976529.png)

 ![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111104001685-1893232430.png)

#### 3.3: 创建tracker基础数据目录

　　　　base_path对应的目录(刚才提到了根目录必须存在,这里就创建根目录)

```
mkdir -p /lulu/fastdfs/tracker
```

#### 3.4: 防火墙配置

　　　　打开跟踪端口（默认的22122）

　　　　(如果禁用了防火墙,请跳过此步)

　　防火墙有两种一种是Iptables另一种是Firewalld

　　自己试是那种哦,选错会报错的!!!

　　A.iptables 的配置

```
vim /etc/sysconfig/iptables

添加如下端口行：
-A INPUT -m state --state NEW -m tcp -p tcp --dport 22122 -j ACCEPT

重启防火墙：
service iptables restart
```

　　B.firewalld的配置

　　　　(博主的CentOS7采用的是这种,博主比较懒,虚拟机环境嘛,要啥防火墙,直接永久禁用了,如果你也禁用了直接跳过这个步骤,如果想禁用,点击下方链接查看具体操作)

[关于Firwalld防火墙的操作点击我查看](https://www.cnblogs.com/hubing/p/6058932.html)

```
添加如下端口
firewall-cmd --zone=public --add-port=22122/tcp --permanent

重新载入
firewall-cmd --reload

查看
firewall-cmd --zone=public --query-port=22122/tcp
```

 

#### 3.5:启动Tracker

　　初次成功启动，会在 /lulu/fdfsdfs/tracker/ (配置的base_path)下创建 data、logs 两个目录。

```
可以用这种方式启动
/etc/init.d/fdfs_trackerd start

也可以用这种方式启动，前提是上面创建了软链接，后面都用这种方式
service fdfs_trackerd start
```

如下图所示,启动成功

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111111006822-1145912212.png)

查看 FastDFS Tracker 是否已成功启动 ，22122端口正在被监听，则算是Tracker服务安装成功。

```
netstat -unltp|grep fdfs
```

如下图所示表示成功启动

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111111119015-1547241569.png)

关闭Tracker命令：

```
service fdfs_trackerd stop
```

如下图所示关闭成功

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111111319248-1063053870.png)

#### 3.6:设置Tracker开机启动

```
chkconfig fdfs_trackerd on

或者：(2选1)
vim /etc/rc.d/rc.local
加入配置：
/etc/init.d/fdfs_trackerd start 
```

#### 3.7:tracker server 目录及文件结构

Tracker服务启动成功后，会在base_path下创建data、logs两个目录。目录结构如下：

```
tree /lulu/fastdfs/tracker/
```

目录结构:

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111112245606-1429067957.png)

 

### 4、配置 FastDFS 存储 (Storage)

#### 4.1:创建副本并重命名

进入 /etc/fdfs 目录，复制 FastDFS 存储器样例配置文件 storage.conf.sample，并重命名为 storage.conf

```
 cd /etc/fdfs
 cp storage.conf.sample storage.conf
 vim storage.conf
```

#### 4.2:编辑storage.conf

标红的需要修改，其它的默认即可。

```
# 配置文件是否不生效，false 为生效``disabled=``false` `# 指定此 storage server 所在 组(卷)``group_name=group1` `# storage server 服务端口``port=23000` `# 心跳间隔时间，单位为秒 (这里是指主动向 tracker server 发送心跳)``heart_beat_interval=30` `# Storage 数据和日志目录地址(根目录必须存在，子目录会自动生成)``base_path=``/lulu/fastdfs/storage` `# 存放文件时 storage server 支持多个路径。这里配置存放文件的基路径数目，通常只配一个目录。``store_path_count=1` `# 逐一配置 store_path_count 个路径，索引号基于 0。``# 如果不配置 store_path0，那它就和 base_path 对应的路径一样。``store_path0=``/lulu/fastdfs/file` `# FastDFS 存储文件时，采用了两级目录。这里配置存放文件的目录个数。``# 如果本参数只为 N（如： 256），那么 storage server 在初次运行时，会在 store_path 下自动创建 N * N 个存放文件的子目录。``subdir_count_per_path=256` `# tracker_server 的列表 ，会主动连接 tracker_server``# 有多个 tracker server 时，每个 tracker server 写一行``tracker_server=images.lulu.com:22122` `# 允许系统同步的时间段 (默认是全天) 。一般用于避免高峰同步产生一些问题而设定。``sync_start_time=00:00``sync_end_time=23:59``# 访问端口``http.server_port=80
```

图示:

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111113636924-1672718883.png)

#### 4.3:创建Storage基础数据目录

　基础目录,对应base_path目录

```
mkdir -p /lulu/fastdfs/storage

这是配置的store_path0路径
mkdir -p /lulu/fastdfs/file
```

 创建成功后的目录结构

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111113827461-583904386.png)

#### 4.4:防火墙配置

打开存储器端口（默认的 23000）

自己试是那种哦,选错会报错的!!!(如果你已禁用防火墙,请忽略该步骤)

A.iptables 的配置

```
vim /etc/sysconfig/iptables

添加如下端口行：
-A INPUT -m state --state NEW -m tcp -p tcp --dport 23000 -j ACCEPT

重启防火墙：
service iptables restart
```

B.firewalld的配置

```
添加如下端口
firewall-cmd --zone=public --add-port=23000/tcp --permanent

重新载入
firewall-cmd --reload

查看
firewall-cmd --zone=public --query-port=23000/tcp
```

#### 4.6:启动 Storage

启动Storage前确保Tracker是启动的。初次启动成功，会在 /lulu/fastdfs/storage 目录下创建 data、 logs 两个目录。

```
可以用这种方式启动
/etc/init.d/fdfs_storaged start

也可以用这种方式，后面都用这种
service fdfs_storaged start
```

 图示:

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111114320855-97806941.png)

 

查看 Storage 是否成功启动，23000 端口正在被监听，就算 Storage 启动成功。

```
netstat -unltp|grep fdfs
```

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111115747376-185822741.png)

关闭Storage命令：

```
service fdfs_storaged stop
```

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111115834047-652933436.png)

查看Storage和Tracker是否在通信：

```
/usr/bin/fdfs_monitor /etc/fdfs/storage.conf
```

 下图是成功的图(多台机器时,你还会看到多台的日志信息)

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111123733862-1469289015.png)

如果状态不是 ACTIVE 比如是 (WAIT_SYNC :等待同步)

请尝试重启:

```
重启 Tracker服务
service fdfs_trackerd restart
重启 Storage服务
service fdfs_storaged restart

再次查看状态
/usr/bin/fdfs_monitor /etc/fdfs/storage.conf
```

 

[如果仍然无法解决您的问题请点击我,获取帮助](http://www.blogjava.net/paulwong/archive/2014/10/13/418683.html)

### 　4.7:设置 Storage 开机启动

```
chkconfig fdfs_storaged on

或者：
vim /etc/rc.d/rc.local
加入配置：
/etc/init.d/fdfs_storaged start
```

#### 4.8:Storage 目录

同 Tracker，Storage 启动成功后，在base_path 下创建了data、logs目录，记录着 Storage Server 的信息。

在 store_path0 目录下，创建了N*N个子目录：

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111035027344-1956688069.png)

### 5、文件上传测试

#### 5.1: 修改 Tracker 服务器中的客户端配置文件 

```
cd /etc/fdfs
cp client.conf.sample client.conf
vim client.conf
```

 修改如下配置即可，其它默认。

```
# Client 的数据和日志目录
base_path=/lulu/fastdfs/client

# Tracker端口
tracker_server=images.lulu.com:22122
```

创建Client基础数据目录，对应base_path目录

```
mkdir -p /lulu/fastdfs/client
```

图示: 

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111121037518-957728467.png)

#### 5.2:上传测试

在linux内部执行如下命令上传提前准备好的namei.jpg 图片

(别闭着眼Copy过去就执行,你好歹准备一张图片测试呀!不然报错哦!!!)

```
/usr/bin/fdfs_upload_file /etc/fdfs/client.conf /root/namei.jpg
```

 上传成功后返回文件ID号：

group1/M00/00/00/CgAABVw4INWAHO9sAAE0dxCBZH8771.jpg

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111125406883-640600947.png)

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111130453627-2017356240.png)

 

## 三、安装Nginx

上面将文件上传成功了，但我们无法下载。因此安装Nginx作为服务器以支持Http方式访问文件。同时，后面安装FastDFS的Nginx模块也需要Nginx环境。

Nginx只需要安装到StorageServer所在的服务器即可，用于访问文件。我这里由于是单机，TrackerServer和StorageServer在一台服务器上。

这里我推荐使用yum安装!

1: 查看笔记：安装Nginx

安装好了记得回来我们继续!!!!!

 

## 四、FastDFS 配置 Nginx 模块

### 1、安装配置Nginx模块

#### 1.1: fastdfs-nginx-module 模块说明

FastDFS 通过 Tracker 服务器，将文件放在 Storage 服务器存储， 但是同组存储服务器之间需要进行文件复制， 有同步延迟的问题。

假设 Tracker 服务器将文件上传到了 192.168.51.128，上传成功后文件 ID已经返回给客户端。

此时 FastDFS 存储集群机制会将这个文件同步到同组存储 192.168.51.129，在文件还没有复制完成的情况下，客户端如果用这个文件 ID 在 192.168.51.129 上取文件,就会出现文件无法访问的错误。

而 fastdfs-nginx-module 可以重定向文件链接到源服务器取文件，避免客户端由于复制延迟导致的文件无法访问错误。

#### 1.2: 下载 fastdfs-nginx-module、解压

```
这里为啥这么长一串呢，因为最新版的master与当前nginx有些版本问题。
wget https://github.com/happyfish100/fastdfs-nginx-module/archive/5e5f3566bbfa57418b5506aaefbe107a42c9fcb1.zip

解压
unzip 5e5f3566bbfa57418b5506aaefbe107a42c9fcb1.zip

重命名
mv fastdfs-nginx-module-5e5f3566bbfa57418b5506aaefbe107a42c9fcb1  fastdfs-nginx-module-master
```

执行命令图:

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111131704292-251825114.png)

#### 1.3: 配置Nginx

在已安装的nginx中动态添加模块

 下载一个同版本的可编译的nginx 

```
额,不确定版本,我们先查看下当前nginx的版本
nginx -v
下载指定版本(nginx版本地址http://nginx.org/download/到这里查看你对应的二版本直接修改下面这个链接的版本号即可)博主的是1.12.2版
wget http://nginx.org/download/nginx-1.12.2.tar.gz
解压并打开
tar zxf nginx-1.12.2.tar.gz && cd nginx-1.12.2
```

 

 操作图:(博主网络不好,请求挂了一次,没事重新来一次即可)

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111132815453-1067309213.png)

 

查看nginx已经安装的模块

```
nginx -V
```

 

 如图,楼主这边已经Nginx自带了这么多模块

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111133035303-1378596034.png)

#### 1.4 避免编译时依赖缺失问题

依赖缺失参考:

缺失PCRE :[点我查看](https://blog.csdn.net/hybaym/article/details/50929958)

缺失OpenSSL:[点我查看](http://www.21yunwei.com/archives/5196)

报错提示HTTP XSLT缺失libxml2/libxslt:[点我查看](http://www.theshell.guru/error-the-http-xslt-module-requires-the-libxml2libxslt-nginx-centos-7-3/)

缺少GD依赖,ExtUtils依赖,GeoIP依赖等 : [点击查看](https://blog.csdn.net/yhahaha_/article/details/83749780)

执行下面命令解决所有依赖问题(至少博主缺的全都在这了)其他的,自行百度查资料解决

```
yum -y install pcre pcre-devel openssl openssl-devel libxslt-devel gd-devel perl-devel perl-ExtUtils-Embed GeoIP GeoIP-devel GeoIP-data google-perftools google-perftools-devel redhat-rpm-config.noarch
```

#### 1.5 开始安装fastdfs-nginx-module 模块

　　如果你是新装的nginx且版本和我一样,直接复制我的使用,如果不是,请根据实际情况

```
./configure --add-module=../fastdfs-nginx-module-master/src --prefix=/usr/share/nginx --sbin-path=/usr/sbin/nginx --modules-path=/usr/lib64/nginx/modules --conf-path=/etc/nginx/nginx.conf --error-log-path=/var/log/nginx/error.log --http-log-path=/var/log/nginx/access.log --http-client-body-temp-path=/var/lib/nginx/tmp/client_body --http-proxy-temp-path=/var/lib/nginx/tmp/proxy --http-fastcgi-temp-path=/var/lib/nginx/tmp/fastcgi --http-uwsgi-temp-path=/var/lib/nginx/tmp/uwsgi --http-scgi-temp-path=/var/lib/nginx/tmp/scgi --pid-path=/run/nginx.pid --lock-path=/run/lock/subsys/nginx --user=nginx --group=nginx --with-file-aio --with-ipv6 --with-http_auth_request_module --with-http_ssl_module --with-http_v2_module --with-http_realip_module --with-http_addition_module --with-http_xslt_module=dynamic --with-http_image_filter_module=dynamic --with-http_geoip_module=dynamic --with-http_sub_module --with-http_dav_module --with-http_flv_module --with-http_mp4_module --with-http_gunzip_module --with-http_gzip_static_module --with-http_random_index_module --with-http_secure_link_module --with-http_degradation_module --with-http_slice_module --with-http_stub_status_module --with-http_perl_module=dynamic --with-mail=dynamic --with-mail_ssl_module --with-pcre --with-pcre-jit --with-stream=dynamic --with-stream_ssl_module --with-google_perftools_module --with-debug --with-cc-opt='-O2 -g -pipe -Wall -Wp,-D_FORTIFY_SOURCE=2 -fexceptions -fstack-protector-strong --param=ssp-buffer-size=4 -grecord-gcc-switches -specs=/usr/lib/rpm/redhat/redhat-hardened-cc1 -m64 -mtune=generic' --with-ld-opt='-Wl,-z,relro -specs=/usr/lib/rpm/redhat/redhat-hardened-ld -Wl,-E'
```

　　fastdfs-nginx-module 模块单独的安装命令(这两个命令二选一)

```
./configure --add-module=../fastdfs-nginx-module-master/src
```

 

 

安装结束如下图(没报错,成功了一大步了)

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111143541255-1043740692.png)

#### 1.5: 编译

```
make -j2 && objs/nginx -t && objs/nginx -V
```

 一顿刷屏后,编译结束,如下图所示:

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111144236576-1352139597.png)

#### 1.6: 文件替换

```
替换文件
cp objs/nginx /usr/sbin/
刷新 
nginx -s reload
```

#### 1.7: 查看Nginx的模块

```
nginx -V
```

 

看到如下图圈中部分,表示安装fastdfs-nginx-module模块成功 

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111153028502-1453058569.png)

#### 1.8: 配置fastdfs-nginx-module

　复制 fastdfs-nginx-module 源码中的配置文件到/etc/fdfs 目录， 并修改

```
打开fastdfs-nginx-module模块的解压文件夹下的src目录
cd /tools/fastdfs-nginx-module-master/src

复制mod_fastdfs.conf 到/etc/fdfs/目录下
cp mod_fastdfs.conf /etc/fdfs/ && ls
```

如图显示,则表示拷贝成功 

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111153452583-306334782.png)

开始修改

```
vim mod_fastdfs.conf
```

 修改如下配置,其他默认

```
# 连接超时时间
connect_timeout=10

# Tracker Server
tracker_server=images.lulu.com:22122

# StorageServer 默认端口
storage_server_port=23000

# 如果文件ID的uri中包含/group**，则要设置为true
url_have_group_name = true

# Storage 配置的store_path0路径，必须和storage.conf中的一致
store_path0=/lulu/fastdfs/file
```

图示:

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111154904482-2051368661.png)

　复制 FastDFS 的部分配置文件到/etc/fdfs 目录 

```
cd /tools/fastdfs-5.05/conf/ && ll

cp anti-steal.jpg http.conf mime.types /etc/fdfs/ && ll /etc/fdfs/
```

 如图:

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111170525496-986384334.png)

 修改nginx.conf配置文件

```
vim /etc/nginx/nginx.conf

        # 将 /group([0-9])正则设置多组 /M00 映射到 /ngx_fastdfs_module模块
        location ~/group([0-9])/M00 {
                ngx_fastdfs_module;
        }
```

图示:

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111155500691-1319866227.png)

 

注意：

listen 80 端口值是要与 /etc/fdfs/storage.conf 中的 http.server_port=80 (前面改成80了)相对应。如果改成其它端口，则需要统一，同时在防火墙中打开该端口。

location 的配置，如果有多个group则配置location ~/group([0-9])/M00 ，没有则不用配group。

在/ljzsg/fastdfs/file 文件存储目录下创建软连接，将其链接到实际存放数据的目录，这一步可以省略。

```
ln -s /lulu/fastdfs/file/data/ /lulu/fastdfs/file/data/M00
```

####  1.9: 启动nginx

```
systemctl start nginx && systemctl status nginx.service
```

成功启动图:

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111160448702-606914694.png)

在地址栏访问

能下载文件就算安装成功。注意和第三点中直接使用nginx路由访问不同的是，这里配置 fastdfs-nginx-module 模块，可以重定向文件链接到源服务器取文件。

![image-20201031162833065](C:\Users\sj\AppData\Roaming\Typora\typora-user-images\image-20201031162833065.png)

 

访问可能也会出错,无法访问,或者出现错误页面,这里列举几个解决的参考地址

注意nginx 的问题请使用如下命令查看,并自己通过打印的日志分析错误原因

```
cat /val/log/nginx/error.log
```

 

关于nginx的访问网络问题:

1: [点我查看参考](https://blog.csdn.net/kriszhang/article/details/68941793) 关于问题1的补充 [点我查看参考](https://www.cnblogs.com/mousean/p/6025220.html)

 

 

 部署后的结构图(盗来的图)：可以按照下面的结构搭建环境

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111173822176-445553444.png)

