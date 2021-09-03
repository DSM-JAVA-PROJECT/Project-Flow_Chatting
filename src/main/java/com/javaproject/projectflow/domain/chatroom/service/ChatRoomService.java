package com.javaproject.projectflow.domain.chatroom.service;

import com.javaproject.projectflow.domain.chatroom.payload.ChatRoomContentResponse;
import com.javaproject.projectflow.domain.chatroom.payload.CreateChatRoomRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatRoomService {
    Mono<String> createChatRoom(String userEmail, CreateChatRoomRequest request, String projectId);
    Flux<ChatRoomContentResponse> getChatRooms(String projectId, String userId);
    Mono<String> resignChatRoom(String projectId, String chatRoomId, String userEmail);
    Mono<String> joinChatRoom(String projectId, String chatRoomId, String userEmail);
}
