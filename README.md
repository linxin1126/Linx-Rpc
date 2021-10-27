# Linx-RPC（轻量级分布式RPC框架）

## 初步技术选型:

1. Spring：依赖注入
2. Netty：简化NIO编程，屏蔽了Java底层的NIO细节
3. Protostuff：基于Protobuf序列化框架，面向POJO，无需编写.proto文件
4. ZooKeeper：提供服务注册与发现功能，分布式系统必备，具备集群能力

## 初步开发流程：

1. 服务端
2. 客户端
3. 客户端代理
4. ZooKeeper 服务注册
5. ZooKeeper 服务发现

## 待完善：

- 需支持异步调用
- 客户端使用TCP长连接(多次调用共享连接）
- TCP心跳连接检测
- 服务端异步多线程处理RPC请求
- 支持不同load balance策略
- 支持不同序列化/反序列化