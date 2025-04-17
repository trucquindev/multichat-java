package com.example.MultiChat.message;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    List<Message> findByReceiverIdAndSenderIdOrSenderIdAndReceiverIdOrderByTimestampAsc(
            String receiverId1, String senderId1, String senderId2, String receiverId2
    );


    List<Message> findByGroupIdOrderByTimestampAsc(String groupId);
}

