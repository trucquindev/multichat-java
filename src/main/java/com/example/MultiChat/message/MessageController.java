package com.example.MultiChat.message;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired private MessageRepository messageRepo;

    @GetMapping("/private/{userId}")
    public List<Message> getPrivateMessages(@PathVariable String userId,
                                            @RequestParam String currentUserId) {
        return messageRepo.findByReceiverIdAndSenderIdOrSenderIdAndReceiverIdOrderByTimestampAsc(
                currentUserId, userId, userId, currentUserId
        );
    }


    @GetMapping("/group/{groupId}")
    public List<Message> getGroupMessages(@PathVariable String groupId) {
        return messageRepo.findByGroupIdOrderByTimestampAsc(groupId);
    }

    @PutMapping("/seen/{messageId}")
    public Message markSeen(@PathVariable String messageId) {
        Message message = messageRepo.findById(messageId).orElseThrow();
        message.setSeen(true);
        return messageRepo.save(message);
    }
}
