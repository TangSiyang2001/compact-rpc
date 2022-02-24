# compact-rpc
(开发中)
#### 介绍
适合框架开发学习和小型项目使用的轻量级rpc框架

#### 开发构想与规划
spi机制

​	ExtensionLoader的实现

客户端
  动态代理

服务端
  
注册中心

​	nacos sdk

通信消息实体

自定义协议

序列化/反序列化实现
    4B  magic code（魔法数）   1B messageType（消息类型） 1B version（版本） 1B compress（压缩类型） 1B codec（序列化类型）  4B  requestId（请求的Id）  4B full length（消息长度）    

负载均衡
  
spring扩展
​	


#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


