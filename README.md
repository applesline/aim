# aim
```
A pure java chatroom based on netty
```
---

#### 一、快速体验

> ##### 1.1 命令行聊天工具aim-cli资源

- 下载地址：[aim-cli.jar](https://www.istart.club/resources/aim-cli.jar)
- 视频教程：[aim-cli使用教程](https://www.istart.club/video/aim-cli%E4%BD%BF%E7%94%A8%E6%95%99%E7%A8%8B.mp4)
- 效果图:   [aim效果图](https://www.istart.club/resources/20200513135343.png)

> ##### 1.2 启动aim-cli
```
java -jar aim-cli.jar
```

> ##### 1.3 使用aim-cli登陆聊天服务器

###### 1.3.1 标准登陆
```
[aim@nologin]# login -h hostname -p 9658 -n test
```

###### 1.3.2 极简登陆
```
[aim@nologin]# login test  #意味着使用test昵称连接到 istart.club:9658 服务器 （暂不可用）
```

###### 1.3.3 登陆参数说明：
- -h  服务器地址，istart.club已经部署过aim-server，aim-cli可直接登陆
- -p  服务端口，9658（意为：965吧，告别996！！！）
- -n  用户昵称，自定义即可

---


#### 二、源码安装
> ##### 2.1 下载源码
```
git clone https://github.com/applesline/aim.git
```
> ##### 2.2 编译源码
```
mvn clean install   #aim-server默认监听9658端口，如果需要改为其他端口，修改application.properties中的配置即可
```
> ##### 2.3 编译结果
```
aim-cli-jar-with-dependencies.jar       #命令行客户端
aim-client-jar-with-dependencies.jar    #图形化客户端
aim-server-jar-with-dependencies.jar    #服务端，默认监听在9658端口
```
> ##### 2.4 启动服务端
```
java -jar aim-server-jar-with-dependencies.jar  
```

> ##### 2.5 启动命令行客户端
```
java -jar aim-cli-jar-with-dependencies.jar
```
> ##### 2.6 启动图形化客户端
```
java -jar aim-cli-jar-with-dependencies.jar
```

---

#### 三、功能特性
- 私有协议
- 对象解码器
- 支持单聊、群聊
- 支持用户登录提醒、登出提醒
- 支持客户端心跳检测
- 支持客户端失败重连
---
#### 四、联系作者
<applesline@163.com>
