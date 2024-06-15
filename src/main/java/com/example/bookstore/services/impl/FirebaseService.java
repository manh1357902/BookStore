package com.example.bookstore.services.impl;

import com.example.bookstore.services.IFirebaseService;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class FirebaseService implements IFirebaseService {
    @Value("${firebase.bucket-name}")
    private String bucketName;
    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Bucket bucket = StorageClient.getInstance().bucket();
        bucket.create(fileName, file.getInputStream(), file.getContentType());
        String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/"+bucketName+"/o/%s?alt=media";
        return String.format(downloadUrl, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }
    @Override
    public void deleteFile(String urlFile) {
        Bucket bucket = StorageClient.getInstance().bucket();
        String prefix = "https://firebasestorage.googleapis.com/v0/b/your-bucket-name/o/";
        String suffix = "?alt=media";
        String fileName = urlFile.substring(prefix.length(), urlFile.indexOf(suffix)).replace("%2F", "/");
        BlobId blobId = BlobId.of(bucketName, fileName);
        bucket.getStorage().delete(blobId);
    }
}
