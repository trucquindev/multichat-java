package com.example.MultiChat.message;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "messages")
public class Message {

    @Id
    private String id;

    private String senderId;
    private String receiverId;  // dùng cho chat cá nhân
    private String groupId;     // dùng cho chat nhóm

    private String content;
    private String type; // text | image | file | emoji

    private boolean seen = false;

    private Date timestamp = new Date();
}
