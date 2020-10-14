# SSM项目集成Swagger2配置

在团队开发中，一个好的API文档不但可以减少大量的沟通成本，还可以帮助前后端分离架构的开发人员更好的沟通。

Swagger2作为一个规范和完整的框架，可以用于生成、描述、调用和可视化RESful风格的web服务。

需求：

​	在已搭建好的SMM（spring+springmvc+mybatis）的项目中整合Swagger2。

搭建步骤：

## 1.引入pom依赖

​	在maven的pom文件中，引入springfox的依赖。

```xml
<!--springfox的核心jar包-->
    <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger2</artifactId>
      <version>2.7.0</version>
    </dependency>
    <!--springfox-ui的jar包(里面包含了swagger的界面静态文件)-->
    <dependency>
      <groupId>io.springfox</groupId>
      <artifactId>springfox-swagger-ui</artifactId>
      <version>2.7.0</version>
    </dependency>
    <!--springfox依赖的jar包；-->
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.9.0</version>
    </dependency>
```

## 2.创建配置类

​	在源码目录下创建一个铭文SwaggerConfig.java配置类；

```java
package com.jzt.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 如果你的项目引入junit测试，此处需要使用@WebAppConfiguration，
 * 如果没有使用junit使用@Configuration(很多的博客都没有注明这个问题，为此我花了非常多的时间解决问题)
 * @author sj
 *
 * @EnableSwagger2：开启Swagger2功能
 * @Configuration：配置类
 * @EnableWebMvc：用于导入springmvc配置
 * @ComponentScan：扫描control所在的package请修改为你control所在package
 */
@EnableSwagger2
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.jzt.controller")
public class SwaggerConfig {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("汉服网项目接口文档")
                .description("汉服网项目接口测试")
                .version("1.0.0")
                .termsOfServiceUrl("")
                .license("")
                .licenseUrl("")
                .build();
    }
}
```

## 3.配置swagger

​	在springmvc的配置文件中配置swagger。

```xml
<!--配置swagger2-->
    <!--将静态资源交由默认的servlet处理-->
    <mvc:default-servlet-handler />
    <!--向容器自动注入配置-->
    <context:annotation-config />
    <!--自动扫描，使springMVC认为包下用了@controller注解的类是控制器-->
    <context:component-scan base-package="com.jzt"/>
    <!--重要！将你的SwaggerConfig配置类注入-->
    <bean class="com.jzt.conf.SwaggerConfig"/>
    <!--重要！配置swagger资源不被拦截-->
    <mvc:resources mapping="swagger-ui.html" location="classpath:/META-INF/resources/" />
    <mvc:resources mapping="/webjars/**" location="classpath:/META-INF/resources/webjars/" />
```

## 4.配置DispatcherServlet前端控制器

​	在web.xml文件中配置所有请求都要经过DispatcherServlet处理。由于我在搭建ssm框架时已经配置。就不在单独配置。

```xml
<!-- 用前端控制器初始化springmvc容器 -->
  <servlet>
    <servlet-name>springDispatcherServlet</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--加载spring-mvc.xml配置文件-->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:springmvc.xml</param-value>
    </init-param>
    <!--启动该服务器，创建该servlet-->
    <load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>springDispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>
```

**注意：**这个地方必须配置，如果你配置的是*.xx的形式会出现api-docs访问出错，这就会导致swagger-ui找不到api的有效路径。使swagger无法正常工作

## 5.在controller上添加对应注解

​	在controller类和对应的接口上添加相应功能的注解。

```java
package com.jzt.controller;

import com.jzt.entity.UserEntity;
import com.jzt.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 视图层（controller）
 * @author sj
 */
@Controller
@RequestMapping("/user")
@Api(value = "/user", tags = "User接口")
public class UserController {
    private static Logger logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    @Autowired
    private IUserService userService;

    @RequestMapping("/findAll")
    @ResponseBody
    @ApiOperation(value = "获取所有用户信息", notes = "获取所有用户信息", httpMethod = "GET", response = UserEntity.class)
    public ResponseEntity findAll(){
        logger.info("表示层：查询所有用户");
        List<UserEntity> userEntityList = userService.findAll();
        logger.info("result : " + userEntityList);
        return ResponseEntity.ok(userEntityList);
    }
}

```

下面启动项目就可以进行测试了。

访问URL：http://localhost:8080/xxxx/swagger-ui.html

![image-20201011162758348](C:\Users\sj\AppData\Roaming\Typora\typora-user-images\image-20201011162758348.png)