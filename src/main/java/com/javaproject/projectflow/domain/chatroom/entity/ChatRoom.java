package com.javaproject.projectflow.domain.chatroom.entity;

import com.javaproject.projectflow.domain.plan.entity.Plan;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Document
public class ChatRoom {

    @MongoId
    private String id;

    private String name;

    private List<Plan> plans;

}