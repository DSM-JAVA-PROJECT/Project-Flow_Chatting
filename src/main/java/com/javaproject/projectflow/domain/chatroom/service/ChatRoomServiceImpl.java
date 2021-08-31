package com.javaproject.projectflow.domain.chatroom.service;

import com.javaproject.projectflow.domain.chatroom.entity.ChatRoom;
import com.javaproject.projectflow.domain.chatroom.entity.ChatRoomRepository;
import com.javaproject.projectflow.domain.chatroom.payload.CreateChatRoomRequest;
import com.javaproject.projectflow.global.rabbit.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.BindingSpecification;
import reactor.rabbitmq.ExchangeSpecification;
import reactor.rabbitmq.Sender;

@RequiredArgsConstructor
@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final Sender sender;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public Mono<String> createChatRoom(String userEmail, CreateChatRoomRequest request, String projectId) {
        return buildChatRoom(request)
                .flatMap(chatRoom -> chatRoomRepository.save(projectId, chatRoom))
                .flatMap(chatRoom -> {
                    sender
                            .declareExchange(ExchangeSpecification.exchange(request.getName())
                                    .type("fanout"));
                    return Mono.just(chatRoom);
                })
                .flatMap(chatRoom -> sender.bindExchange(BindingSpecification
                        .exchangeBinding(RabbitMQConfig.DIRECT_EXCHANGE, chatRoom.getId(), chatRoom.getId())
                ))
                .map(unused -> "success");
    }

    private Mono<ChatRoom> buildChatRoom(CreateChatRoomRequest request) {
        return Mono.just(ChatRoom.builder()
                .name(request.getName())
                .build());
    }

}
