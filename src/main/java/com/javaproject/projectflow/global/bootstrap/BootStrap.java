package com.javaproject.projectflow.global.bootstrap;

import com.javaproject.projectflow.global.rabbit.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import reactor.rabbitmq.ExchangeSpecification;
import reactor.rabbitmq.Sender;

@RequiredArgsConstructor
public class BootStrap implements CommandLineRunner {

    private final Sender sender;

    @Override
    public void run(String... args) {
        sender.declareExchange(ExchangeSpecification
                .exchange(RabbitMQConfig.DIRECT_EXCHANGE)
                .type("direct"))
                .subscribe();
    }
}
