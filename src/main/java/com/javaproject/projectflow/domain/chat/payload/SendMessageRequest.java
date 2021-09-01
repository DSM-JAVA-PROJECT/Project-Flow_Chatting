package com.javaproject.projectflow.domain.chat.payload;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SendMessageRequest {
    private String message;

    private String senderEmail;

    private String senderName;

    private String senderImage;

    private LocalDateTime createdAt;
}
