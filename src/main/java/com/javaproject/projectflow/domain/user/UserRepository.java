package com.javaproject.projectflow.domain.user;

import com.javaproject.projectflow.domain.chatroom.entity.ChatRoom;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<ChatRoom, String> {
}
