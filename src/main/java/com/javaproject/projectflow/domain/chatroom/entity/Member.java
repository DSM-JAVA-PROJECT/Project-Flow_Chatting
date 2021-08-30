package com.javaproject.projectflow.domain.chatroom.entity;

import com.javaproject.projectflow.domain.user.User;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
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

    @DBRef
    private User user;

    private String field;

    private String phoneNumber;

}
