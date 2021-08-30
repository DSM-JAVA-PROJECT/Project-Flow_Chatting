package com.javaproject.projectflow.domain.chatroom.entity;

import com.javaproject.projectflow.domain.plan.entity.Plan;
import lombok.*;
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

    private List<Plan> plans;

}