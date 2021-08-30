package com.javaproject.projectflow.domain.chatroom.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Document
public class OwnProject {

    @MongoId
    private String id;

    private String projectName;

    private LocalDate startAt;

    private LocalDate endAt;

    private boolean status;

}
