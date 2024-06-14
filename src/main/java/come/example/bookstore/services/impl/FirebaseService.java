package come.example.bookstore.services.impl;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import come.example.bookstore.services.IFirebaseService;
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
}
