package com.example.bookstore.dto.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryRequest {
    @NotEmpty(message = "Name is required.")
    private String name;
}
