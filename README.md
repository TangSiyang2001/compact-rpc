# compact-rpc

#### 项目简介

- 适合框架开发学习和小型项目使用的轻量级rpc框架。
- 主要功能模式类似于知名rpc框架dubbo。
- 该项目麻雀虽小，五脏俱全，孵化于作者学习中间件开发的过程中。跟随本项目的实现思路能够使读者快速掌握一个rpc框架的基本原理和设计思想。
- 为了更加方便地理解本项目，您需要对[Netty](https://netty.io/)（通信框架）和[Nacos](https://nacos.io/)（注册中心）稍作了解。

#### 项目模块

- **compact-rpc-base**：该项目的基础模块，实现了项目通用的接口、注解、实体和工具。
- **compact-rpc-impl**：该项目的主要模块，实现了该rpc框架的通信协议、序列化机制，服务注册与发现，服务端、客户端通信等主体功能。
- **compact-rpc-spring**：该项目的拓展模块，实现与原生spring框架的适配，能够伴随ApplicationContext自启动，同时根据注解情况自动完成服务注册，支撑远程过程调用。
- 带有**compact-rpc-test**前缀的均为测试模块。
- **compact-rpc-test-api**：服务端与客户端共同依赖的服务接口模块。
- **compact-rpc-test-client**：客户端样例。
- **compact-rpc-test-server**：服务端样例。

#### 快速开始

[下载Nacos](https://github.com/alibaba/nacos/releases)

**在本地启动Nacos单机版**

首先先在命令行中切换到相应安装目录下

```bash
cd nacos/bin
```

运行

###### Windows

```bash
startup.cmd -m standalone
```

###### Linux/Unix/Mac

```bash
sh startup.sh -m standalone
```

如果您使用的是ubuntu系统，或者运行脚本报错提示[[符号找不到，可尝试如下运行：

```bash
bash startup.sh -m standalone
```

![image-20220422230615761](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220422230615761.png)

观察到nacos启动成功

##### 克隆项目到本地

等待依赖导入成功后，运行服务端测试（com.tsy.rpc.test.server.App的main方法）

![image-20220422231304094](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220422231304094.png)



![image-20220422232146205](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220422232146205.png)

正常启动

![image-20220422231600599](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220422231600599.png)

若服务端没有出现异常退出，运行客户端测试（com.tsy.rpc.test.client.App的main方法）

![image-20220422231711768](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220422231711768.png)



![image-20220422232106090](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220422232106090.png)

控制台打印出hello rpc!字样，测试成功。

![image-20220422231902456](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20220422231902456.png)



#### 主要实现内容

- spi机制

  ​	ExtensionLoader的实现

- 客户端 动态代理

- 服务端

- 注册中心

  ​	nacos sdk

- 通信消息实体

- 自定义协议

  4B  magic code（魔法数）   1B messageType（消息类型） 1B version（版本）  1B compress（压缩类型） 1B codec（序列化类型）  4B  requestId（请求的Id）  4B full  length（消息长度）

- 序列化/反序列化实现

- protostuff

- 负载均衡

- spring扩展

#### 设计与实现思路

待更新