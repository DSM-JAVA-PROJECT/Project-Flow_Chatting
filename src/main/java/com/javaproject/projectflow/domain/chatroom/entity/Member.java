package com.javaproject.projectflow.domain.chatroom.entity;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Member {

    private String userId;

    private String userName;

    private String field;

    private String phoneNumber;

    private List<OwnProject> ownProjectList;

}
