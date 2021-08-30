package com.javaproject.projectflow.domain.plan.entity;

import com.javaproject.projectflow.domain.chatroom.entity.Member;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Document
public class Plan {

    @MongoId
    private String id;

    private String name;

    private LocalDate endDate;

    private LocalDateTime createdAt;

    private List<Member> members;

}