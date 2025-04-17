package com.example.MultiChat.group;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "groups")
public class Group {

    @Id
    private String id;

    private String name;

    private String createdBy;
    private List<String> memberIds;
}
