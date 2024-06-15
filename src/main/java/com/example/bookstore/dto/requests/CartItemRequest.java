package com.example.bookstore.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest {
    @NotNull(message = "Quantity is required.")
    private Integer quantity;
    private Long bookId;
}
