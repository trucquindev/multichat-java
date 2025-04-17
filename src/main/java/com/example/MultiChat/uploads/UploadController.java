package com.example.MultiChat.uploads;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired private CloudinaryService cloudinaryService;

    @PostMapping
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        return cloudinaryService.upload(file);
    }
}
