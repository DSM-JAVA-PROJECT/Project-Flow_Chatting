package com.javaproject.projectflow.domain.chatroom.controller;

import com.javaproject.projectflow.domain.chatroom.payload.ChatRoomContentResponse;
import com.javaproject.projectflow.domain.chatroom.payload.CreateChatRoomRequest;
import com.javaproject.projectflow.domain.chatroom.service.ChatRoomService;
import com.javaproject.projectflow.global.security.CurrentUser;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @MessageMapping("{projectId}.chatroom.add")
    public Mono<String> createChatRoom(@DestinationVariable @NonNull String projectId, @CurrentUser String email,
                                       @Payload CreateChatRoomRequest request) {
        return chatRoomService.createChatRoom(email, request, projectId);
    }

    @MessageMapping("{projectId}.chatroom")
    public Flux<ChatRoomContentResponse> getChatRoom(@DestinationVariable @NonNull String projectId, @CurrentUser String email) {
        return chatRoomService.getChatRooms(projectId, email);
    }

    @MessageMapping("resign.{chatRoomId}")
    public Mono<String> resignChatRoom(@DestinationVariable @NonNull String chatRoomId, @CurrentUser String email) {
        return chatRoomService.resignChatRoom(chatRoomId, email);
    }

    @MessageExceptionHandler
    public Mono<ResponseEntity<String>> exception(final Exception exception) {
        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage()));
    }

}
