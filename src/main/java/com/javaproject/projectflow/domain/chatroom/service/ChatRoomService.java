package com.javaproject.projectflow.domain.chatroom.service;

import com.javaproject.projectflow.domain.chatroom.payload.CreateChatRoomRequest;
import reactor.core.publisher.Mono;

public interface ChatRoomService {
    Mono<String> createChatRoom(String userEmail, CreateChatRoomRequest request, String projectId);
}
