package utc2.itk62.e_reader.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.model.CreateBookParam;
import utc2.itk62.e_reader.domain.model.UpdateBookParam;
import utc2.itk62.e_reader.dto.BookResponse;
import utc2.itk62.e_reader.dto.CreateBookRequest;
import utc2.itk62.e_reader.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<HTTPResponse<BookResponse>> create(@RequestPart("file")MultipartFile file,
                                                             @RequestPart("data")CreateBookRequest request) {
        CreateBookParam createBookParam = CreateBookParam.builder()
                .file(file)
                .title(request.getTitle())
                .author(request.getAuthor())
                .genre(request.getGenre())
                .language(request.getLanguage())
                .publishedYear(request.getPublishedYear())
                .totalPage(request.getTotalPage())
                .build();
        Book book = bookService.createBook(createBookParam);
        BookResponse bookResponse = BookResponse.builder()
                .language(book.getLanguage())
                .title(book.getTitle())
                .id(book.getId())
                .author(book.getAuthor())
                .publishedYear(book.getPublishedYear())
                .genre(book.getGenre())
                .totalPage(book.getTotalPage())
                .fileUrl(book.getFileUrl())
                .build();
        return HTTPResponse.ok(bookResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HTTPResponse<BookResponse>> update(@RequestPart("file")MultipartFile file,
                                                            @RequestPart("data")CreateBookRequest request,
                                                             @PathVariable Long id) {
        UpdateBookParam updateBookParam = UpdateBookParam.builder()
                .file(file)
                .publishedYear(request.getPublishedYear())
                .title(request.getTitle())
                .genre(request.getGenre())
                .language(request.getLanguage())
                .author(request.getAuthor())
                .totalPage(request.getTotalPage())
                .id(id)
                .build();

        Book book = bookService.updateBook(updateBookParam);
        BookResponse bookResponse = BookResponse.builder()
                .language(book.getLanguage())
                .title(book.getTitle())
                .id(book.getId())
                .author(book.getAuthor())
                .publishedYear(book.getPublishedYear())
                .genre(book.getGenre())
                .totalPage(book.getTotalPage())
                .fileUrl(book.getFileUrl())
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
                .publishedYear(book.getPublishedYear())
                .genre(book.getGenre())
                .totalPage(book.getTotalPage())
                .fileUrl(book.getFileUrl())
                .build();
        return HTTPResponse.ok(bookResponse);
    }
    @GetMapping
    public ResponseEntity<HTTPResponse<List<BookResponse>>> getAllBook() {

        List<BookResponse> bookResponseList = bookService.getAllBook()
                .stream().map(book -> BookResponse.builder()
                        .id(book.getId())
                        .fileUrl(book.getFileUrl())
                        .language(book.getLanguage())
                        .title(book.getTitle())
                        .totalPage(book.getTotalPage())
                        .genre(book.getGenre())
                        .publishedYear(book.getPublishedYear())
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
