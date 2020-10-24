# Mybatis分页查询插件PageHelper简单入门

​	最近在在搭建一个SSM项目时需要做一个分页查询功能。一开始想着去自己实现分页功能，自己定义一个PageBean之类的，然后手动实现。但是通过查询相关资料发现，MyBatis有一个实现分页功能的插件PageHelper，能够快速的帮助我们实现分页，不用我们自己动手，避免了很多的重复繁琐工作。

直接进入正题，下面我们就来聊一聊PageHelper。

## 1.PageHelper简介

官网：[ https://pagehelper.github.io/](https://pagehelper.github.io/)

![image-20201024195150356](C:\Users\sj\AppData\Roaming\Typora\typora-user-images\image-20201024195150356.png)

一款好用的开源免费的Mybatis第三方物理分页插件。

## 2.使用方法

### 2.1引入插件依赖

PageHelper引入依赖的方式有2种，一种是引入jar，一种是通过Maven。我的项目时通过Maven方式。

#### 1）引入jar包

你可以从下面的地址中下载最新版本的 jar 包

- https://oss.sonatype.org/content/repositories/releases/com/github/pagehelper/pagehelper/
- http://repo1.maven.org/maven2/com/github/pagehelper/pagehelper/

由于使用了sql 解析工具，你还需要下载 jsqlparser.jar：

- http://repo1.maven.org/maven2/com/github/jsqlparser/jsqlparser/0.9.5/

#### 2）使用Maven

在pom.xml中引入：

```xml
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper</artifactId>
    <version>最新版本</version>
</dependency>
```

### 2.2配置拦截器插件

在spring配置文件中添加如下内容：

```xml
<!--配置sqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"></property>
        <!--pagehelper分页插件-->
        <property name="plugins">
            <array>
                <bean class="com.github.pagehelper.PageInterceptor">
                    <property name="properties">
                        <!-- config params as the following -->
                        <value>
                            param1=value1
                        </value>
                    </property>
                </bean>
            </array>
        </property>
    </bean>
```

注意插件是com.github.pagehelper.PageInterceptor。

## 3.代码实现

在service业务层通过如下方式调用：

```java
 @Override
    public PageInfo<PostPageEntity> findAllByPage(int currPage, int pageSize) {
        PageHelper.startPage(currPage, pageSize);
        List<PostPageEntity> postPageEntityList = postPageDao.findAllByPage();
        PageInfo<PostPageEntity> pageInfo = new PageInfo<>(postPageEntityList);
        return pageInfo;
    }
```

controller

```java
@RequestMapping("/findAll2")
    @ResponseBody
    @ApiOperation(value = "获取所有页面集合信息（分页）", notes = "获取所有页面集合信息（分页）", httpMethod = "GET", response = PostPageEntity.class)
    public ResponseEntity findAll2(@RequestParam("currPage") Integer currPage, @RequestParam("pageSize") Integer pageSize){
        PageInfo<PostPageEntity> postPageEntityList = null;
        try{
            postPageEntityList = postPageService.findAllByPage(currPage, pageSize);
            logger.info("result : " + postPageEntityList);
        }catch (Exception e){
            logger.error("[findAll] find all users error:"+e.getMessage(), e);
        }
        return ResponseEntity.ok(postPageEntityList);
    }
```

## 4.测试

![image-20201024200900392](C:\Users\sj\AppData\Roaming\Typora\typora-user-images\image-20201024200900392.png)

可以发现已经含有分页信息了。