package com.javaproject.projectflow.domain.chatroom.entity;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ChatRoomRepository extends ReactiveMongoRepository<ChatRoom, String> {
}
