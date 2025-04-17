package com.example.MultiChat.auth;

import com.example.MultiChat.config.JwtUtil;
import com.example.MultiChat.user.User;
import com.example.MultiChat.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse register(AuthRequest request) {
        if (userRepo.findByEmail(request.getEmail()) != null) {
            throw new RuntimeException("Email already registered.");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepo.save(user);

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token, user);
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepo.findByEmail(request.getEmail());
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password.");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token, user);
    }
}
