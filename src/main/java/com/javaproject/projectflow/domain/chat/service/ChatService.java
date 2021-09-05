package com.javaproject.projectflow.domain.chat.service;

import com.javaproject.projectflow.domain.chat.payload.ChatRequest;
import reactor.core.publisher.Mono;

public interface ChatService {
    Mono<String> sendMessage(String projectId, String chatRoomId, ChatRequest request, String sender);
}
