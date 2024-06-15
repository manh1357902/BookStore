package com.example.bookstore.controllers;

import com.example.bookstore.dto.requests.BookRequest;
import com.example.bookstore.dto.responses.BookResponse;
import com.example.bookstore.dto.responses.ExceptionResponse;
import com.example.bookstore.services.IBookService;
import com.example.bookstore.services.IFirebaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final IBookService bookService;
    private final IFirebaseService firebaseService;
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<BookResponse> bookResponses = bookService.getAllBooks();
        return new ResponseEntity<>(bookResponses, HttpStatus.OK);
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getBooksByCategory(@PathVariable("categoryId") Long categoryId){
        try{
            List<BookResponse> bookResponses = bookService.getBooksByCategory(categoryId);
            return new ResponseEntity<>(bookResponses, HttpStatus.OK);
        }catch (Exception e){
            ExceptionResponse exceptionResponse = ExceptionResponse.builder().message(e.getMessage()).build();
            return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable("id") Long id){
        try {
            BookResponse bookResponses = bookService.getBookById(id);
            return new ResponseEntity<>(bookResponses, HttpStatus.OK);
        } catch (Exception e) {
            ExceptionResponse exceptionResponse = ExceptionResponse.builder().message(e.getMessage()).build();
            return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(value = "/add",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> addBook(@Valid @ModelAttribute BookRequest bookRequest){
        try{
            String url = firebaseService.uploadFile(bookRequest.getImage());
            BookResponse bookResponse = bookService.addBook(url, bookRequest);
            return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
        }catch (Exception e){
            ExceptionResponse exceptionResponse = ExceptionResponse.builder().message(e.getMessage()).build();
            return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateBook(@PathVariable("id") Long id,@Valid @ModelAttribute BookRequest bookRequest){
        try{
            if(bookRequest.getImage()!=null){
                String url = firebaseService.uploadFile(bookRequest.getImage());
                BookResponse bookResponse = bookService.updateBook(id, bookRequest, url);
                return new ResponseEntity<>(bookResponse, HttpStatus.OK);
            }else{
                BookResponse bookResponse = bookService.updateBook(id, bookRequest, null);
                return new ResponseEntity<>(bookResponse, HttpStatus.OK);
            }

        }catch (Exception e){
            ExceptionResponse exceptionResponse = ExceptionResponse.builder().message(e.getMessage()).build();
            return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id){
        try{
            bookService.deleteBook(id);
            return new  ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            ExceptionResponse exceptionResponse = ExceptionResponse.builder().message(e.getMessage()).build();
            return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
