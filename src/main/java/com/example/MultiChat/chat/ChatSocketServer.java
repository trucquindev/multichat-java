package com.example.MultiChat.chat;
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.listener.*;
import com.example.MultiChat.message.Message;
import com.example.MultiChat.message.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.*;

@Component
public class ChatSocketServer {

    @Autowired private MessageRepository messageRepo;

    private SocketIOServer server;

    @PostConstruct
    public void init() {
        Configuration config = new Configuration();
        config.setHostname("0.0.0.0");
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "3000"));
        config.setPort(port);
        config.setOrigin("*");
        config.setAllowCustomRequests(true);


        server = new SocketIOServer(config);

        server.addConnectListener(client -> {
            String userId = client.getHandshakeData().getSingleUrlParam("userId");
            client.set("userId", userId);
            System.out.println("User connected: " + userId);
        });

        server.addDisconnectListener(client -> {
            String userId = client.get("userId");
            System.out.println("User disconnected: " + userId);
        });

//        server.addEventListener("private_message", Map.class, (client, data, ackSender) -> {
//            String from = (String) data.get("from");
//            String to = (String) data.get("to");
//            String content = (String) data.get("content");
//
//            Message message = new Message();
//            message.setSenderId(from);
//            message.setReceiverId(to);
//            message.setContent(content);
//            message.setType("text");
//            messageRepo.save(message);
//
//            server.getAllClients().forEach(c -> {
//                if (to.equals(c.get("userId")) || from.equals(c.get("userId"))) {
//                    c.sendEvent("new_private_message", message);
//                }
//            });
//        });
        server.addEventListener("private_message", Map.class, (client, data, ackSender) -> {
            String from = (String) data.get("from");
            String to = (String) data.get("to");
            String content = (String) data.get("content");

            // Lưu tin nhắn vào cơ sở dữ liệu
            Message message = new Message();
            message.setSenderId(from);
            message.setReceiverId(to);
            message.setContent(content);
            message.setType("text");
            messageRepo.save(message);

            // Gửi sự kiện tin nhắn tới các client đúng
            server.getAllClients().forEach(c -> {
                String userId = (String) c.get("userId");
                if (to.equals(userId) || from.equals(userId)) {
                    c.sendEvent("new_private_message", message);
                }
            });
        });


        server.addEventListener("group_message", Map.class, (client, data, ackSender) -> {
            String from = (String) data.get("from");
            String groupId = (String) data.get("groupId");
            String content = (String) data.get("content");

            Message message = new Message();
            message.setSenderId(from);
            message.setGroupId(groupId);
            message.setContent(content);
            message.setType("text");
            messageRepo.save(message);

            server.getAllClients().forEach(c -> {
                c.sendEvent("new_group_message", message);
            });
        });

        server.start();
    }
}
