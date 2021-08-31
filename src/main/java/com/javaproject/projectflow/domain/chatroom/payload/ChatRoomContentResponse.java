package com.javaproject.projectflow.domain.chatroom.payload;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ChatRoomContentResponse {

    private String chatRoomId;

    private String chatRoomName;

    private boolean isParticipated;

}
