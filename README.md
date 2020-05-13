# aim
```
A pure java chatroom based on netty
```

#### 一、快速体验

> ##### 1.1 命令行聊天工具aim-cli资源

- 下载地址：[aim-cli.jar](ftp://istart.club/aim-cli/aim-cli.jar)
- 视频教程：[aim-cli使用教程](ftp://istart.club/aim-cli/aim-cli%E4%BD%BF%E7%94%A8%E6%95%99%E7%A8%8B.mp4)

> ##### 1.2 启动aim-cli
```
java -jar aim-cli.jar

```

> ##### 1.3 使用aim-cli登陆聊天服务器

```
[aim@nologin]# login -h istart.club -p 9658 -n test

```
>> 登陆参数说明：
- -h  服务器地址，istart.club已经部署过aim-server，aim-cli可直接登陆
- -p  服务端口，9658（意为：965吧，告别996！！！）
- -n  用户昵称，自定义即可
---
>> 极简登陆
```
[aim@nologin]# login test  #意味着使用test昵称连接到 istart.club:9658 服务器
```
---

#### 二、源码安装

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
