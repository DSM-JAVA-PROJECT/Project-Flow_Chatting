package com.javaproject.projectflow.domain.plan.entity;

import com.javaproject.projectflow.domain.CreatedAtEntity;
import com.javaproject.projectflow.domain.chatroom.entity.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Document
public class Plan extends CreatedAtEntity {

    @MongoId
    private String id;

    @NonNull
    private String name;

    @NonNull
    @Field("end_date")
    private LocalDate endDate;

    @NonNull
    private List<Member> members;

}