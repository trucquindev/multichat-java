package com.example.MultiChat.group;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateGroupRequest {
    private String name;
    private String creatorId;
    private List<String> memberIds;
}
