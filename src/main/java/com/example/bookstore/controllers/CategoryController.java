package com.example.bookstore.controllers;

import com.example.bookstore.dto.requests.CategoryRequest;
import com.example.bookstore.dto.responses.CategoryResponse;
import com.example.bookstore.dto.responses.ExceptionResponse;
import com.example.bookstore.services.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final ICategoryService categoryService;
    @GetMapping
    public ResponseEntity<?> getAllCategories(){
        List<CategoryResponse> categoryResponses = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id){
        try{
            CategoryResponse categoryResponse = categoryService.getCategoryById(id);
            return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
        }catch (Exception e){
            ExceptionResponse exceptionResponse = ExceptionResponse.builder().message(e.getMessage()).build();
            return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        try {
            CategoryResponse categoryResponse = categoryService.addCategory(categoryRequest);
            return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
        }catch (Exception e){
            ExceptionResponse exceptionResponse = ExceptionResponse.builder().message(e.getMessage()).build();
            return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id,@Valid @RequestBody CategoryRequest categoryRequest){
        try {
            CategoryResponse categoryResponse = categoryService.updateCategory(id, categoryRequest);
            return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
        }catch (Exception e){
            ExceptionResponse exceptionResponse = ExceptionResponse.builder().message(e.getMessage()).build();
            return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id){
        try {
            categoryService.deleteCategory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            ExceptionResponse exceptionResponse = ExceptionResponse.builder().message(e.getMessage()).build();
            return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
