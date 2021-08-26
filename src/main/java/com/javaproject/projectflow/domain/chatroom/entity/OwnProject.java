package com.javaproject.projectflow.domain.chatroom.entity;

import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class OwnProject {

    private String projectName;

    private LocalDate startAt;

    private LocalDate endAt;

    private boolean status;

}
