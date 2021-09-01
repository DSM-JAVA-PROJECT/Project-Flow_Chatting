package com.javaproject.projectflow.domain.chat.controller;

import com.javaproject.projectflow.domain.chat.payload.ChatRequest;
import com.javaproject.projectflow.domain.chat.service.ChatService;
import com.javaproject.projectflow.global.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("{projectId}.{chatRoomId}.message")
    public Mono<String> sendMessage(@DestinationVariable String projectId,
                                    @DestinationVariable String chatRoomId,
                                    @Payload ChatRequest request,
                                    @CurrentUser String email) {
        return chatService.sendMessage(projectId, chatRoomId, request, email);
    }

}
