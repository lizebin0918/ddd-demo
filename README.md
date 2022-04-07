# ddd-demo

## 注意事项
* 一个事务只能更新一个聚合根
* 一个聚合根的更新只会有一个事件
* app层说明做什么，domain层说明怎么做

## 实体命名
* 持久化对象（`Po-persisten object`）：字段与数据库一一对应
* 外部传参（`Query-查询;Cmd-更新`）
* 内部数据流转对象（`Dto-data transfer object`）
* 返回给前端对象（`Info`）
* 值对象（`Vo-value object`）：ddd的值对象，不定义view object，用Info代替