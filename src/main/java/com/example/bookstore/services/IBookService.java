package com.example.bookstore.services;

import com.example.bookstore.dto.requests.BookRequest;
import com.example.bookstore.dto.responses.BookResponse;

import java.io.IOException;
import java.util.List;

public interface IBookService {
    List<BookResponse> getAllBooks();
    List<BookResponse> getBooksByCategory(long categoryId) throws Exception;
    BookResponse getBookById(long id) throws Exception;
    BookResponse addBook(String imageUrl, BookRequest bookRequest) throws IOException;
    BookResponse updateBook(long id, BookRequest bookRequest, String imageUrl) throws Exception;
    void deleteBook(long id) throws Exception;
}
