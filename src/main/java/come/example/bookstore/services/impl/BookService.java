package come.example.bookstore.services.impl;

import come.example.bookstore.dto.requests.BookRequest;
import come.example.bookstore.dto.responses.BookResponse;
import come.example.bookstore.models.entities.Book;
import come.example.bookstore.models.entities.Category;
import come.example.bookstore.repositories.BookRepository;
import come.example.bookstore.repositories.CategoryRepository;
import come.example.bookstore.services.IBookService;
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
    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream().map(book -> modelMapper.map(book, BookResponse.class)).collect(Collectors.toList());
    }

    @Override
    public List<BookResponse> getBooksByCategory(long categoryId) throws Exception {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new Exception("Category not found with id: "+ categoryId));
        return bookRepository.findByCategories(Collections.singletonList(category)).stream().map(book -> modelMapper.map(book, BookResponse.class)).collect(Collectors.toList());
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
    public BookResponse updateBook(BookRequest bookRequest) {
        return null;
    }

    @Override
    public void deleteBook(long id) {

    }
}
