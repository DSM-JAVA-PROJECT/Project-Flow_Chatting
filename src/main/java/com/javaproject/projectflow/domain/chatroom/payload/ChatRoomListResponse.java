package com.javaproject.projectflow.domain.chatroom.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class ChatRoomListResponse {
    private List<ChatRoomContentResponse> chatRoomContentResponse;
}
