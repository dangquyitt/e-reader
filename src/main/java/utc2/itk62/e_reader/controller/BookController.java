package utc2.itk62.e_reader.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.dto.BookResponse;
import utc2.itk62.e_reader.dto.CreateBookRequest;
import utc2.itk62.e_reader.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/create")
    public ResponseEntity<HTTPResponse<BookResponse>> create(@RequestPart("file")MultipartFile file,
                                                             @RequestPart("data")CreateBookRequest request) {
        Book book = bookService.createBook(file,request);
        BookResponse bookResponse = BookResponse.builder()
                .language(book.getLanguage())
                .title(book.getTitle())
                .id(book.getId())
                .author(book.getAuthor())
                .year(book.getYear())
                .genre(book.getGenre())
                .numberOfPages(book.getNumberOfPages())
                .file(book.getFile())
                .build();
        return HTTPResponse.ok(bookResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HTTPResponse<BookResponse>> update(@RequestPart("file")MultipartFile file,
                                                            @RequestPart("data")CreateBookRequest request,
                                                             @PathVariable Long id) {
        Book book = bookService.updateBook(file,request,id);
        BookResponse bookResponse = BookResponse.builder()
                .language(book.getLanguage())
                .title(book.getTitle())
                .id(book.getId())
                .author(book.getAuthor())
                .year(book.getYear())
                .genre(book.getGenre())
                .numberOfPages(book.getNumberOfPages())
                .file(book.getFile())
                .build();
        return HTTPResponse.ok(bookResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HTTPResponse<BookResponse>> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        BookResponse bookResponse = BookResponse.builder()
                .language(book.getLanguage())
                .title(book.getTitle())
                .id(book.getId())
                .author(book.getAuthor())
                .year(book.getYear())
                .genre(book.getGenre())
                .numberOfPages(book.getNumberOfPages())
                .file(book.getFile())
                .build();
        return HTTPResponse.ok(bookResponse);
    }
    @GetMapping
    public ResponseEntity<HTTPResponse<List<BookResponse>>> getAllBook() {

        List<BookResponse> bookResponseList = bookService.getAllBook()
                .stream().map(book -> BookResponse.builder()
                        .id(book.getId())
                        .file(book.getFile())
                        .language(book.getLanguage())
                        .title(book.getTitle())
                        .numberOfPages(book.getNumberOfPages())
                        .genre(book.getGenre())
                        .year(book.getYear())
                        .author(book.getAuthor())
                        .build()).collect(Collectors.toList());

        return HTTPResponse.ok(bookResponseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HTTPResponse<String>> deleteBook(@PathVariable Long id) {

        String message = "";
        if(bookService.deleteBook(id)){
            message = "Delete book successfully";
        }

        return HTTPResponse.ok(message);
    }
}
