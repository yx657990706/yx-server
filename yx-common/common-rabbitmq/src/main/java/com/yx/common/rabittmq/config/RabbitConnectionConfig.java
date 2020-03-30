package com.yx.common.rabittmq.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 *  MQ的连接设置
 */
@Slf4j
@Configuration
public class RabbitConnectionConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    /**
     * 消费者数量
     */
    @Value("${report.consumers:1}")
    private Integer consumers;
    /**
     *  最大消费者数量
     */
    @Value("${report.maxConsumers:1}")
    private Integer maxConsumers;
    /**
     * prefetchCount<br>
     *     如果消费者对应的channel上未ack的消息数达到了prefetch_count的个数,暂时不再接受消息投递
     */
    @Value("${report.prefetchCount:1}")
    private Integer prefetchCount;
    /**
     * 通道等待的秒数
     */
    @Value("${report.receiveTimeout:15000}")
    private Long receiveTimeout;

    /**
     * 消息转换器
     *
     * @return
     */
    @Bean(name = "ReportMessageConverter")
    public MessageConverter messageConverter() {
        //消息转换时，移除null属性，避免ES中覆盖的问题
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return new Jackson2JsonMessageConverter(mapper);
//        return new Jackson2JsonMessageConverter();
    }

    /**
     * 连接接工厂设置<br>
     * 连接属性自动注入.当存在多个ConnectionFactory，以此为默认的
     *
     * @return
     */
    @Primary
    @Bean(name = "ReportConnectionFactory")
    @ConfigurationProperties(prefix = "report.rabbitmq")
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        //设置消息发送ack，默认是none
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }

    @Bean(name = "ReportListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(@Qualifier("ReportConnectionFactory") ConnectionFactory connectionFactory,
                                                                               @Qualifier("ReportMessageConverter") MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setConcurrentConsumers(this.consumers);
        factory.setMaxConcurrentConsumers(this.maxConsumers);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setPrefetchCount(this.prefetchCount);
        factory.setReceiveTimeout(this.receiveTimeout);
        return factory;
    }

    @Bean(name="ReportRabbitTemplate")
    public RabbitTemplate rabbitTemplate( @Qualifier("ReportMessageConverter") MessageConverter converter,
                                          @Qualifier("ReportConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMandatory(true);
        template.setEncoding("UTF-8");
        template.setReturnCallback(this);
        template.setConfirmCallback(this);
        template.setMessageConverter(converter);
        return template;
    }

    @Bean(name="ReportRabbitAdmin")
    public RabbitAdmin rabbitAdmin(@Qualifier("ReportConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    /**
     * producer->broker->exchange->queue->consumer
     * 从 producer->broker 触发 confirmCallback(消息发送到exchange，ConfirmCallback回调的ack=true)
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            log.error("confirm error,原因: {}", cause);
        }
    }

    /**
     * producer->broker->exchange->queue->consumer
     * 从 exchange->queue 投递失败则会触发 returnCallback
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.warn("ReturnCallback:{},{},{},{},{}", message, replyCode, replyText, exchange, routingKey);
    }
}

