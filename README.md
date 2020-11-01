#  搭建汉服网

我爱汉服网   www.52hanfu.com

## 1.架构

后端技术选型：

​	spring+springmvc+mybatis+maven+redis+mysql+elaticsearch

## 2.功能模块

![image-20200830220814891](C:\Users\sj\AppData\Roaming\Typora\typora-user-images\image-20200830220814891.png)

写真：展示汉服相关图片；以日期排序。以专辑为一套写真页面。包含收藏、点赞等功能。

红人：以汉服红人为专题展示；即人物的主页。这个主页包含该红人的所有作品集。人物主页包含收藏、关注等功能。

美妆：主要是一些汉服美妆的教程；视频和图片均可。

发型：主要是一些汉服搭配发型的教程；

社区：关于汉服的一些社区活动。

商城：关联汉服商城；

其他功能：登录、注册等功能。

## 3.项目结构

```
hanfuweb
	├── code 			    	-- 代码
		├── web_back 			-- 后端源码
			├── web_client 		-- 后端前台代码（用户界面接口）
			├── web_server 		-- 后端后台代码（管理界面接口）
		├── web_front 			-- 前端代码
	├── docs 					-- 文档
		├── devDoc 				-- 开发相关手册
		└── share 				-- 技术总结与分享
```



## 4.页面设计

### 4.1首页

效果图：

![image-20201023234336701](C:\Users\sj\AppData\Roaming\Typora\typora-user-images\image-20201023234336701.png)

​	首页是网站的门面。需要选择好主题颜色基调和设计LOGO。主要包含的功能：

​	（1）菜单选择

​	（2）全站搜索

​	（3）注册、登录

​	（4）轮转图片（6个轮片）

​	（5）展示最新图片集（以一个文章页面的封面图片）

​	（6）展示最新的文章集



登录页面：

![image-20201011083236601](C:\Users\sj\AppData\Roaming\Typora\typora-user-images\image-20201011083236601.png)

注册页面：

![image-20201011083314166](C:\Users\sj\AppData\Roaming\Typora\typora-user-images\image-20201011083314166.png)

11









