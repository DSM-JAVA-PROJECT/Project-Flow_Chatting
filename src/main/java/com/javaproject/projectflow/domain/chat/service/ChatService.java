package com.javaproject.projectflow.domain.chat.service;

import com.javaproject.projectflow.domain.chat.payload.ChatRequest;
import com.javaproject.projectflow.domain.chat.payload.SendMessageRequest;
import reactor.core.publisher.Mono;

public interface ChatService {
    Mono<String> sendMessage(String projectId, String chatRoomId, ChatRequest request, String sender);
}
