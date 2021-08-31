package com.javaproject.projectflow.domain.chatroom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class ChatRoomController {

    @MessageExceptionHandler
    public Mono<ResponseEntity<String>> exception(final Exception exception)
    {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage()));
    }

}
