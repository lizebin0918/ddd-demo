# ddd-demo

## 业务场景
- 商品添加到购物车
- 购物车结算，下单。下单需要判断库存是否充足、需要锁定库存

## 项目信息
- 技术栈：Java 21、Spring Boot 3.2、PostgreSQL 14、Redis 6.x、MyBatis-Plus、Docker、maven

## 上下文模块说明
  - component、test-component相关工具类
  - inventory-context: 库存上下文
  - order-context: 订单上下文（订单、购物车）
  - product-context: 产品上下文

## 基于COLA的ddd分层架构（微调）
![image](https://github.com/lizebin0918/ddd-demo/blob/main/ddd%E5%88%86%E5%B1%82.drawio.png)

## 限界上下文分层
### `order-context-adapter`：端口+适配层（方向：从外到内）
  - `eventhandler`：消息消费
  - `web`：资源入口（定义Controller）
  - `task`：定时任务
### `order-context-app`：应用层

- `OrderAppService`：按用例维度拆开或者用`OrderAppService`
  - `OrderQueryAppService`：查询入口类，调用多个查询服务 
  - `dto`：数据传输对象（cmd对象、接收前端的参数）
  - `service`：查询服务（一个查询可能会调用多个服务）
  - `vo`:以View结尾，返回给前端的视图对象
### `order-context-domain`：领域层（聚合根、实体、值对象、领域服务、领域事件、枚举定义）
  - `aggregate`：聚合根（包含读模型）
- `dto`：数据传输对象、命令对象（Cmd结尾）
  - `event`：领域事件
  - `factory`：工厂，用于创建领域模型
  - `repository`：仓储，只定义接口
  - `service`：领域服务（需要依赖外部业务）
### `order-context-infr`：基础设施层（实际也是适配层，方向：从内到外）
  - `common`：通用类 
  - `adapter`：适配器（实现domain、app的接口）
  - `convertor`：转换器（领域模型<->数据模型、dto<->领域模型）
  - `persistence`：持久化
  - `client`：远程调用客户端

## 运行条件
- 安装docker环境[下载地址](https://www.docker.com/)
- Java 21+

## 本地启动
- start
  - `com.lzb.Application`
- 自动化测试
  - 在命令行执行：`mvn verify`，将运行测试

## lombok
- 全局配置文件：`lombok.config`
- 通过全参构造+Builder实例化领域对象（聚合根、实体、值对象），为了方便测试推荐声明包内setter-`@Setter(AccessLevel.PACKAGE)`

## mybatis-plus
- 配置类：`MybatisPlusConfig`，注意：全局的更新和插入策略
  - `dbConfig.setUpdateStrategy(FieldStrategy.ALWAYS);`
  - `dbConfig.setInsertStrategy(FieldStrategy.NOT_EMPTY);`
- 所有持久化模型以`Po`结尾，并且继承`BasePo`，包含`addTime`、`updateTime`
- Po的枚举字段类型，统一继承`EnumValue`，在`DefaultEnumValueTypeHandler`转换

## 测试相关
- 单元测试继承：`BaseUnitTest`
- mapper单元测试：`BaseMapperUnitTest`
- controller单元测试：`BaseControllerUnitTest`
- 集成测试继承：`BaseIntegrationTest`
- 命名规范：`被测类名 + 被测方法名 + Unit/Integration + Test`
