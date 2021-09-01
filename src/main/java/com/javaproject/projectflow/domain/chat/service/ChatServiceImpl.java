package com.javaproject.projectflow.domain.chat.service;

import com.javaproject.projectflow.domain.chat.entity.Chat;
import com.javaproject.projectflow.domain.chat.entity.ChatRepository;
import com.javaproject.projectflow.domain.chat.payload.ChatRequest;
import com.javaproject.projectflow.domain.chat.payload.SendMessageRequest;
import com.javaproject.projectflow.domain.chatroom.entity.ChatRoom;
import com.javaproject.projectflow.domain.chatroom.entity.ChatRoomRepository;
import com.javaproject.projectflow.domain.user.UserRepository;
import com.javaproject.projectflow.global.jackson.ReactiveObjectMapper;
import com.javaproject.projectflow.global.rabbit.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final Sender sender;
    private final ReactiveObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Override
    public Mono<String> sendMessage(String projectId, String chatRoomId, ChatRequest request, String sender) {
        return chatRoomRepository.getChatRoom(projectId, chatRoomId)
                .flatMap(chatRoom -> buildChat(sender, request, chatRoom))
                .flatMap(chatRepository::save)
                .flatMap(chat -> sendMessage(chat.getChatRoom(), buildMessage(chat)))
                .map(unused -> "created");
    }

    private Mono<Chat> buildChat(String sender, ChatRequest request, ChatRoom chatRoom) {
        return userRepository.findById(sender)
                .map(user ->
                        Chat.builder()
                                .chatRoom(chatRoom)
                                .sender(user)
                                .message(request.getMessage())
                                .readers(new ArrayList<>())
                                .build()
                );
    }

    private SendMessageRequest buildMessage(Chat chat) {
        return SendMessageRequest.builder()
                .message(chat.getMessage())
                .createdAt(chat.getCreatedAt())
                .senderEmail(chat.getSender().getEmail())
                .senderName(chat.getSender().getName())
                .senderImage(chat.getSender().getProfileImage())
                .build();
    }

    private Mono<Void> sendMessage(ChatRoom chatRoom, SendMessageRequest request) {
        return objectMapper.encodeValue(SendMessageRequest.class, Mono.just(request))
                .map(dataBuffer -> Mono.just(new OutboundMessage(RabbitMQConfig.DIRECT_EXCHANGE, chatRoom.getId(), dataBuffer.asByteBuffer().array())))
                .flatMap(sender::send)
                .then();
    }

}
