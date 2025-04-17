package com.example.MultiChat.user;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/me")
    public User getCurrentUser(HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        return userRepo.findById(userId).orElse(null);
    }

    @GetMapping("/search")
    public List<User> searchUser(@RequestParam String username) {
        return userRepo.findAll().stream()
                .filter(u -> u.getUsername().toLowerCase().contains(username.toLowerCase()))
                .toList();
    }

    @PutMapping("/avatar")
    public User updateAvatar(HttpServletRequest request, @RequestBody UpdateAvatarRequest body) {
        String userId = (String) request.getAttribute("userId");
        User user = userRepo.findById(userId).orElseThrow();
        user.setAvatarUrl(body.getAvatarUrl());
        return userRepo.save(user);
    }
}
