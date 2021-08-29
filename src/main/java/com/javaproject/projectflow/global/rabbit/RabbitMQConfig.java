package com.javaproject.projectflow.global.rabbit;

import com.rabbitmq.client.Connection;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.*;

@Configuration
public class RabbitMQConfig {

    public static final String DIRECT_EXCHANGE = "chat.direct";

    @Bean
    public Mono<Connection> connectionMono(CachingConnectionFactory connectionFactory) {    // RabbitConnection 생성
        return Mono.fromCallable(() -> connectionFactory
                .getRabbitConnectionFactory().newConnection());
    }

    @Bean
    Sender sender(Mono<Connection> connectionMono) {
        return RabbitFlux.createSender(new SenderOptions().connectionMono(connectionMono));
    }

    @Bean
    Receiver receiver(Mono<Connection> connectionMono) {
        return RabbitFlux.createReceiver(new ReceiverOptions().connectionMono(connectionMono));
    }

}
