package com.example.MultiChat.chat;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @GetMapping("/ping")
    public String test() {
        return "SocketIO Server running!";
    }
}
