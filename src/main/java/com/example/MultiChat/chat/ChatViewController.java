package com.example.MultiChat.chat;

import com.example.MultiChat.user.User;
import com.example.MultiChat.user.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ChatViewController {

    @Autowired private UserRepository userRepo;

    @GetMapping("/chat")
    public String chatPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        List<User> others = userRepo.findAll().stream()
                .filter(u -> !u.getId().equals(user.getId()))
                .toList();

        model.addAttribute("users", others);
        return "chat";
    }
}
