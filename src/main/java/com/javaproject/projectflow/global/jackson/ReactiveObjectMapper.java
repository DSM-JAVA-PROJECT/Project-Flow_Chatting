package com.javaproject.projectflow.global.jackson;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ReactiveObjectMapper {
    private final Jackson2JsonEncoder jsonEncoder;
    private final DataBufferFactory dataBufferFactory;

    public Flux<DataBuffer> encodeValue(Class<?> target, Mono<Object> targetObj) {
        ResolvableType type = ResolvableType.forType(target);
        if (jsonEncoder.canEncode(type, null)) {
            return jsonEncoder.encode(targetObj, dataBufferFactory,
                    ResolvableType.forClass(target), null, null);
        }
        return null;
    }

}
