# java操作FastDFS的API



## 五、Java客户端

前面文件系统平台搭建好了，现在就要写客户端代码在系统中实现上传下载，这里只是简单的测试代码。

### 1、首先需要搭建 FastDFS 客户端Java开发环境

#### 　　1.1: maven依赖

项目中使用maven进行依赖管理，可以在pom.xml中引入如下依赖即可：

```
        <!-- https://mvnrepository.com/artifact/cn.bestwu/fastdfs-client-java -->
        <dependency>
            <groupId>cn.bestwu</groupId>
            <artifactId>fastdfs-client-java</artifactId>
            <version>1.27</version>
        </dependency>
```

 

其它的方式，参考官方文档：[点我直达](https://github.com/happyfish100/fastdfs-client-java)

#### 　　1.2: 引入配置文件

 整合配置文件:

可直接复制包下的 fastdfs-client.properties.sample 或者 fdfs_client.conf.sample，到你的项目中，去掉.sample。

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111175956143-1379951207.png)

我这里直接复制 fastdfs-client.properties.sample 中的配置到项目配置文件 application.properties 中，修改tracker_servers。只需要加载这个配置文件即可

### 2、客户端API

大佬封装的FastDFS Java API [点我下载](https://pan.baidu.com/s/1nixJ3CNmAzaEdaTBu7ORcg) 

 

### 六、权限控制

前面使用nginx支持http方式访问文件，但所有人都能直接访问这个文件服务器了，所以做一下权限控制。

FastDFS的权限控制是在服务端开启token验证，客户端根据文件名、当前unix时间戳、秘钥获取token，在地址中带上token参数即可通过http方式访问文件。

#### 　　6.1: 服务端开启token验证

```
修改http.conf
vim /etc/fdfs/http.conf

设置为true表示开启token验证
http.anti_steal.check_token=true

设置token失效的时间单位为秒(s)
http.anti_steal.token_ttl=1800

密钥，跟客户端配置文件的fastdfs.http_secret_key保持一致(这个KEY可以自定义)
http.anti_steal.secret_key=FASTDFS20190111

如果token检查失败，返回的页面
http.anti_steal.token_check_fail=/lulu/fastdfs/page/403.html
```

记得重启服务。

#### 　　6.2: 配置客户端

客户端只需要设置如下两个参数即可，两边的密钥保持一致。

```
 token 防盗链功能
fastdfs.http_anti_steal_token=true
 密钥
fastdfs.http_secret_key=FASTDFS20190111
```

 

![img](https://img2018.cnblogs.com/blog/1399677/201901/1399677-20190111180136012-1519250922.png)

#### 　　6.3: 客户端生成token

访问文件需要带上生成的token以及unix时间戳，所以返回的token是token和时间戳的拼接。

之后，将token拼接在地址后即可访问：images.lulu.com/group1/M00/00/00/wKgzgFnkaXqAIfXyAAEoRmXZPp878.jpeg?token=078d370098b03e9020b82c829c205e1f&ts=1508141521

```
　　/**
     * 获取访问服务器的token，拼接到地址后面
     *
     * @param filepath 文件路径 group1/M00/00/00/wKgzgFnkTPyAIAUGAAEoRmXZPp876.jpeg
     * @param httpSecretKey 密钥
     * @return 返回token，如： token=078d370098b03e9020b82c829c205e1f&ts=1508141521
     */
    public static String getToken(String filepath, String httpSecretKey){
        // unix seconds
        int ts = (int) Instant.now().getEpochSecond();
        // token
        String token = "null";
        try {
            token = ProtoCommon.getToken(getFilename(filepath), ts, httpSecretKey);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("token=").append(token);
        sb.append("&ts=").append(ts);

        return sb.toString();
    }
```

 

#### 　　6.4: 注意事项

如果生成的token验证无法通过，请进行如下两项检查：
　　A. 确认调用token生成函数(ProtoCommon.getToken)，传递的文件ID中没有包含group name。传递的文件ID格式形如：M00/00/00/wKgzgFnkTPyAIAUGAAEoRmXZPp876.jpeg

　　B. 确认服务器时间基本是一致的，注意服务器时间不能相差太多，不要相差到分钟级别。

以上就是单机中使用FastDFS搭建文件系统并上传下载的过程。