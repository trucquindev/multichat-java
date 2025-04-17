package com.example.MultiChat.auth;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
    private String username; // required in register
}
