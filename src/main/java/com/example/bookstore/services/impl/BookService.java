package com.example.bookstore.services.impl;

import com.example.bookstore.repositories.BookRepository;
import com.example.bookstore.repositories.CartItemRepository;
import com.example.bookstore.repositories.CategoryRepository;
import com.example.bookstore.services.IBookService;
import com.example.bookstore.services.IFirebaseService;
import com.example.bookstore.dto.requests.BookRequest;
import com.example.bookstore.dto.responses.BookResponse;
import com.example.bookstore.models.entities.Book;
import com.example.bookstore.models.entities.CartItem;
import com.example.bookstore.models.entities.Category;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final IFirebaseService firebaseService;
    private final CartItemRepository cartItemRepository;
    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findByIsActive(true).stream().map(book -> modelMapper.map(book, BookResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getBooksByCategory(long categoryId) throws Exception {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new Exception("Category not found with id: "+ categoryId));
        return bookRepository.findByCategoriesAndIsActive(Collections.singletonList(category),true).stream().map(book -> modelMapper.map(book, BookResponse.class)).collect(Collectors.toList());
    }

    @Override
    public BookResponse getBookById(long id) throws Exception {
        Book book = bookRepository.findById(id).orElseThrow(()-> new Exception("Book not found."));
        return modelMapper.map(book, BookResponse.class);
    }

    @Override
    @Transactional
    public BookResponse addBook(String imageUrl, BookRequest bookRequest) throws IOException {
        Book book = Book.builder()
                .title(bookRequest.getTitle())
                .price(bookRequest.getPrice())
                .author(bookRequest.getAuthor())
                .quantity(bookRequest.getQuantity())
                .description(bookRequest.getDescription())
                .publisher(bookRequest.getPublisher())
                .image(imageUrl)
                .isActive(true)
                .categories(categoryRepository.findAllById(bookRequest.getCategoryIds()))
                .build();
        return modelMapper.map(bookRepository.save(book), BookResponse.class);
    }

    @Override
    @Transactional
    public BookResponse updateBook(long id, BookRequest bookRequest, String imageUrl) throws Exception {
        Book book = bookRepository.findById(id).orElseThrow(() -> new Exception("Category not found."));
        book.setTitle(bookRequest.getTitle());
        book.setPrice(bookRequest.getPrice());
        book.setAuthor(bookRequest.getAuthor());
        book.setQuantity(bookRequest.getQuantity());
        book.setDescription(bookRequest.getDescription());
        book.setPublisher(bookRequest.getPublisher());
        if(bookRequest.getImage()!=null){
            firebaseService.deleteFile(book.getImage());
            book.setImage(imageUrl);
        }
        book.setCategories(categoryRepository.findAllById(bookRequest.getCategoryIds()));
        return modelMapper.map(bookRepository.save(book), BookResponse.class);
    }

    @Override
    public void deleteBook(long id) throws Exception {
        Book book = bookRepository.findById(id).orElseThrow(()-> new Exception("Book not found."));
        List<CartItem> cartItems = cartItemRepository.findByBook(book);
        cartItemRepository.deleteAll(cartItems);
        book.setIsActive(false);
    }
}
