package com.example.bookstore.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFirebaseService {

    String uploadFile(MultipartFile file) throws IOException;
    void deleteFile(String fileName);
}
