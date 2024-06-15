package com.example.bookstore.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    @NotEmpty(message = "Title is required.")
    private String title;
    @NotNull(message = "Price is required.")
    private Double price;
    @NotEmpty(message = "Author is required.")
    private String author;
    private MultipartFile image;
    @NotNull(message = "Price is required.")
    private Integer quantity;
    private String description;
    private String publisher;
    private Boolean isActive;
    private List<Long> categoryIds;
}
