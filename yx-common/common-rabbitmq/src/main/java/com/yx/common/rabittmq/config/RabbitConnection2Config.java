//package com.yx.commonrabittmq.config;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.AcknowledgeMode;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
///**
// * @Author jesse
// * @Date 3/30/20 5:19 下午
// * 第二个mq地址
// **/
//@Slf4j
//@Configuration
//public class RabbitConnection2Config implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{
//    /**
//     * 消费者数量
//     */
//    @Value("${game.consumers:1}")
//    private Integer consumers;
//    /**
//     *  最大消费者数量
//     */
//    @Value("${game.maxConsumers:1}")
//    private Integer maxConsumers;
//    /**
//     * prefetchCount<br>
//     *     如果消费者对应的channel上未ack的消息数达到了prefetch_count的个数,暂时不再接受消息投递
//     */
//    @Value("${game.prefetchCount:1}")
//    private Integer prefetchCount;
//    /**
//     * 通道等待的秒数
//     */
//    @Value("${game.receiveTimeout:15000}")
//    private Long receiveTimeout;
//
//    /**
//     * 消息转换器
//     *
//     * @return
//     */
//    @Bean(name = "GameMessageConverter")
//    public MessageConverter messageConverter() {
//        //消息转换时，移除null属性，避免ES中覆盖的问题
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        return new Jackson2JsonMessageConverter(mapper);
////        return new Jackson2JsonMessageConverter();
//    }
//
//    /**
//     * 连接接工厂设置<br>
//     * 连接属性自动注入.当存在多个ConnectionFactory，以此为默认的
//     *
//     * @return
//     */
//    @Primary
//    @Bean(name = "GameConnectionFactory")
//    @ConfigurationProperties(prefix = "game.rabbitmq")
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        //connectionFactory.setPublisherConfirms(true);
//        //设置消息发送ack，默认是none
//        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
//        connectionFactory.setPublisherReturns(true);
//        return connectionFactory;
//    }
//
//    @Bean(name = "GameListenerContainerFactory")
//    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(@Qualifier("GameConnectionFactory") ConnectionFactory connectionFactory,
//                                                                               @Qualifier("GameMessageConverter") MessageConverter messageConverter) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConnectionFactory(connectionFactory);
//        factory.setMessageConverter(messageConverter);
//        factory.setConcurrentConsumers(this.consumers);
//        factory.setMaxConcurrentConsumers(this.maxConsumers);
//        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        factory.setPrefetchCount(this.prefetchCount);
//        factory.setReceiveTimeout(this.receiveTimeout);
//        return factory;
//    }
//
//    @Bean(name="GameRabbitTemplate")
//    public RabbitTemplate rabbitTemplate( @Qualifier("GameMessageConverter") MessageConverter converter,
//                                          @Qualifier("GameConnectionFactory") ConnectionFactory connectionFactory) {
//        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        template.setMandatory(true);
//        template.setEncoding("UTF-8");
//        template.setReturnCallback(this);
//        template.setConfirmCallback(this);
//        template.setMessageConverter(converter);
//        return template;
//    }
//
//    @Bean(name="GameRabbitAdmin")
//    public RabbitAdmin rabbitAdmin(@Qualifier("GameConnectionFactory") ConnectionFactory connectionFactory) {
//        return new RabbitAdmin(connectionFactory);
//    }
//
//    /**
//     * producer->broker->exchange->queue->consumer
//     * 从 producer->broker 触发 confirmCallback(消息发送到exchange，ConfirmCallback回调的ack=true)
//     */
//    @Override
//    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//        if (!ack) {
//            log.error("confirm error,原因: {}", cause);
//        }
//    }
//
//    /**
//     * producer->broker->exchange->queue->consumer
//     * 从 exchange->queue 投递失败则会触发 returnCallback
//     */
//    @Override
//    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//        log.warn("ReturnCallback:{},{},{},{},{}", message, replyCode, replyText, exchange, routingKey);
//    }
//}
