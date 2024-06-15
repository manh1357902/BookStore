package com.example.bookstore.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
    private Double price;
    private String author;
    private String image;
    private Integer quantity;
    private String description;
    private String publisher;
    private List<CategoryResponse> categories;
}
