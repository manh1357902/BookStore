package come.example.bookstore.controllers;

import come.example.bookstore.dto.requests.BookRequest;
import come.example.bookstore.dto.responses.BookResponse;
import come.example.bookstore.dto.responses.ExceptionResponse;
import come.example.bookstore.services.IBookService;
import come.example.bookstore.services.IFirebaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final IBookService bookService;
    private final IFirebaseService firebaseService;
//    @GetMapping
//    public ResponseEntity<?> getAll(){
//
//    }
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
}
