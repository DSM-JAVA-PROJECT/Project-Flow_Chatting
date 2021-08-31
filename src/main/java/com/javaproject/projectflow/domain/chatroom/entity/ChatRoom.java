package com.javaproject.projectflow.domain.chatroom.entity;

import com.javaproject.projectflow.domain.plan.entity.Plan;
import com.javaproject.projectflow.domain.user.User;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ChatRoom {

    @MongoId
    private String id;

    private String name;

    @DBRef(lazy = true)
    private List<User> users;

    private List<Plan> plans;

}