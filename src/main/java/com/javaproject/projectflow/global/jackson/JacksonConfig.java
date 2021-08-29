package com.javaproject.projectflow.global.jackson;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Configuration
public class JacksonConfig {

    private final Jackson2JsonEncoder jsonEncoder;
    private final DataBufferFactory dataBufferFactory;

    @Bean
    Flux<DataBuffer> encodeValue(Class<?> target, Mono<Object> targetObj) {
        ResolvableType type = ResolvableType.forType(target);
        if(jsonEncoder.canEncode(type, null)) {
            return jsonEncoder.encode(targetObj, dataBufferFactory,
                    ResolvableType.forClass(target), null, null);
        }
        return null;
    }
}
