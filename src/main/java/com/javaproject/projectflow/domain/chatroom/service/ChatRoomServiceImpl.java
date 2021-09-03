package com.javaproject.projectflow.domain.chatroom.service;

import com.javaproject.projectflow.domain.chatroom.entity.ChatRoom;
import com.javaproject.projectflow.domain.chatroom.entity.ChatRoomRepository;
import com.javaproject.projectflow.domain.chatroom.payload.ChatRoomContentResponse;
import com.javaproject.projectflow.domain.chatroom.payload.CreateChatRoomRequest;
import com.javaproject.projectflow.global.rabbit.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.BindingSpecification;
import reactor.rabbitmq.ExchangeSpecification;
import reactor.rabbitmq.QueueSpecification;
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
                    sender.declareExchange(ExchangeSpecification.exchange(request.getName())
                            .type("fanout"));
                    return Mono.just(chatRoom);
                })
                .flatMap(chatRoom -> {
                    sender.bindExchange(BindingSpecification
                            .exchangeBinding(RabbitMQConfig.DIRECT_EXCHANGE, chatRoom.getId(), chatRoom.getId()));
                    return Mono.just(chatRoom);
                })
                .flatMap(chatRoom -> joinChatRoom(projectId, chatRoom.getId(), userEmail))
                .map(unused -> "created");
    }

    @Override
    public Flux<ChatRoomContentResponse> getChatRooms(String projectId, String userEmail) {
        return chatRoomRepository.findChatRooms(projectId)
                .map(chatRoom -> buildChatRoom(chatRoom, userEmail));
    }

    @Override
    public Mono<String> resignChatRoom(String projectId, String chatRoomId, String userEmail) {
        String chatRoom = chatRoomId + "." + userEmail;
        return sender.deleteQueue(QueueSpecification.queue(chatRoom), true, true)
                .map(unused -> "no content");
    }

    @Override
    public Mono<String> joinChatRoom(String projectId, String chatRoomId, String userEmail) {
        String queueName = chatRoomId + "." + userEmail;

        return sender.declareQueue(QueueSpecification
                        .queue()
                        .name(queueName))
                .flatMap(unused -> sender.bindQueue(BindingSpecification.queueBinding(chatRoomId, userEmail, queueName)))
                .map(unused -> "created");
    }

    private ChatRoomContentResponse buildChatRoom(ChatRoom chatRoom, String userEmail) {
        return ChatRoomContentResponse.builder()
                .chatRoomName(chatRoom.getName())
                .chatRoomId(chatRoom.getId())
                .isParticipated(chatRoom.getUsers().stream().anyMatch(user -> user.getEmail().equals(userEmail)))
                .build();
    }

    private Mono<ChatRoom> buildChatRoom(CreateChatRoomRequest request) {
        return Mono.just(ChatRoom.builder()
                .name(request.getName())
                .build());
    }

}
