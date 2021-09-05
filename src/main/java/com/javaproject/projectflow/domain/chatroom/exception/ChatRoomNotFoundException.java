package com.javaproject.projectflow.domain.chatroom.exception;

public class ChatRoomNotFoundException extends RuntimeException {
    public static final RuntimeException EXCEPTION = new ChatRoomNotFoundException();

    private ChatRoomNotFoundException() {
        super("ChatRoom Not Found");
    }
}
