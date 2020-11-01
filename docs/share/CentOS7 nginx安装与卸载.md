# CentOS7 nginx安装与卸载

​	本教程中的步骤要求用户拥有root权限

### 一. 安装

####  1.1:添加Nginx存储库

​     要添加CentOS EPEL仓库,终端使用yum命令安装,直接复制执行:

```
sudo yum -y install epel-release
```

 

#### 1.2:安装Nginx

​    现在Nginx存储库已经安装在您的服务器上，使用以下`yum`命令安装Nginx ：

```
sudo yum -y install nginx
```

#### 1.3:启动Nginx

　　Nginx不会自动启动.要运行Nginx,请输入:

```
sudo systemctl start nginx
```

　　如果您正在运行防火墙，请运行以下命令以允许HTTP和HTTPS通信：(如果防火墙关了,可直接跳过)

#### 1.4:设置防火墙

#####  1.4.1:允许http通信

```
sudo firewall-cmd --permanent --zone=public --add-service=http
```

##### 1.4.2:允许https通信

```
sudo firewall-cmd --permanent --zone=public --add-service=https
```

##### 1.4.3:重启防火墙

```
sudo firewall-cmd --reload
```

#### 1.5:测试

　　浏览器里键入你的主机地址

 ![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111013414924-119654197.png)

　　如果看到这个页面，那么你的Web服务器现在已经正确安装了。

　　如果想在系统启动时启用Nginx。请输入以下命令：

```
sudo systemctl enable nginx
```

　　恭喜你!!! Nginx现在已经成功安装并允许了!!

 

### 二 . 卸载

#### 1:停止Nginx软件

```
service nginx stop
```

#### 2:删除Nginx的自动启动

```
chkconfig nginx off
```

#### 3:从源头删除Nginx

```
rm -rf /usr/sbin/nginx
rm -rf /etc/nginx
rm -rf /etc/init.d/nginx
```

#### 4:再使用yum清理

```
yum remove nginx
```

 　ok结束。