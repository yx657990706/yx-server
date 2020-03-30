### MQ说明
##### 1.ES上报类的MQ
Producer 
1.生产者发送失败时，支持指数退避算法。
2.已经设置好默认的exchange和routeKey。即上报的队列已经固定
3.持久化支持


#### 2.一般业务异步用的MQ
MessageProducer 
1.exchange和routeKey动态传入，灵活。
2.提供通用发送方法和延迟发送方法
3.可以注入exchange和routeKey，定制不同的个性化生产者


#### RabbitConnectionConfig
MQ的连接配置，包括消息转换器、连接工厂、监听、rabbitTemplate和admin。
支持连接多个不同的MQ。只需要拷贝RabbitConnectionConfig。调整连接参数即可。
RabbitConnection2Config是多连接的demo。

#### RabbitBindConfig
队列绑定配置。实现队列与交换机的绑定。
建议按照常规做法。一个交换机对应多个队列，用routekey做路由。不要一个交换机对一个队列，太浪费性能。





#### 备注
AMQP无法直接创建host。默认的host是"/"。如果需要创建host，建议通过控制台创建。

