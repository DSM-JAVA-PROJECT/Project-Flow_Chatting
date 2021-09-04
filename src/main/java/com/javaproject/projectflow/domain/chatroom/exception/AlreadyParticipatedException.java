package com.javaproject.projectflow.domain.chatroom.exception;

public class AlreadyParticipatedException extends RuntimeException {
    public static final RuntimeException EXCEPTION = new AlreadyParticipatedException();

    private AlreadyParticipatedException() {
        super("User Already Participated");
    }
}
