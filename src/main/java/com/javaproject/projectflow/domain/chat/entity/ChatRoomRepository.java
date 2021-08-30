package com.javaproject.projectflow.domain.chat.entity;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ChatRoomRepository extends ReactiveMongoRepository<Chat, String> {
}
