package com.example.bookstore.dto.responses;

import com.example.bookstore.models.entities.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {
    private Long id;
    private Integer quantity;
    private Book book;
    private Double price;
}
