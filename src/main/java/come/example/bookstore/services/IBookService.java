package come.example.bookstore.services;

import come.example.bookstore.dto.requests.BookRequest;
import come.example.bookstore.dto.responses.BookResponse;

import java.io.IOException;
import java.util.List;

public interface IBookService {
    List<BookResponse> getAllBooks();
    List<BookResponse> getBooksByCategory(long categoryId) throws Exception;
    BookResponse addBook(String imageUrl, BookRequest bookRequest) throws IOException;
    BookResponse updateBook(BookRequest bookRequest);
    void deleteBook(long id);
}
