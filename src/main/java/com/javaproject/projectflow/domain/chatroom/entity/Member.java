package com.javaproject.projectflow.domain.chatroom.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Document
public class Member {

    @MongoId
    private String id;

    private String userId;

    private String userName;

    private String field;

    private String phoneNumber;

}
