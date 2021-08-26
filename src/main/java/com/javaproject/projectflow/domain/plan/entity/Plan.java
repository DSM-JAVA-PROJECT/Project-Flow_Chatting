package com.javaproject.projectflow.domain.plan.entity;

import com.javaproject.projectflow.domain.chatroom.entity.ChatRoom;
import com.javaproject.projectflow.domain.chatroom.entity.Member;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Document
public class Plan {

    @DBRef(lazy = true)
    private ChatRoom chatRoom;

    private String name;

    private LocalDate endDate;

    private List<Member> members;

}