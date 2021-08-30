package com.javaproject.projectflow.domain.chatroom.entity;

import com.javaproject.projectflow.domain.plan.entity.Plan;
import lombok.*;
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
    private String chatRoomId;

    private String projectId;

    private String chatRoomName;

    private List<Member> members;

    private List<Plan> plans;

    private String adminUser;

}