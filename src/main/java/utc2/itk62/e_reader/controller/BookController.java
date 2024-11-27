package utc2.itk62.e_reader.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.model.CreateBookParam;
import utc2.itk62.e_reader.domain.model.UpdateBookParam;
import utc2.itk62.e_reader.dto.BookResponse;
import utc2.itk62.e_reader.dto.CreateBookRequest;
import utc2.itk62.e_reader.dto.UpdateBookRequest;
import utc2.itk62.e_reader.service.BookService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")

@AllArgsConstructor
public class BookController {

    private final BookService bookService;
    private final MessageSource messageSource;



    @PostMapping
    public ResponseEntity<HTTPResponse> create(@ModelAttribute CreateBookRequest request, Locale locale) {
        CreateBookParam createBookParam = CreateBookParam.builder()
                .fileBook(request.getFileBook())
                .fileCoverImage(request.getFileCoverImage())
                .title(request.getTitle())
                .desc(request.getDesc())
                .totalPage(request.getTotalPage())
                .rating(request.getRating())
                .publishedYear(request.getPublishedYear())
                .build();
        Book book = bookService.createBook(createBookParam);
        BookResponse bookResponse = BookResponse.builder()
                .title(book.getTitle())
                .id(book.getId())
                .desc(book.getDescription())
                .rating(book.getRating())
                .publishedYear(book.getPublishedYear())
                .totalPage(book.getTotalPage())
                .fileUrl(book.getFileUrl())
                .build();

        String message = messageSource.getMessage("book.create.success", null, locale);
        return HTTPResponse.success(message,bookResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HTTPResponse> update(@ModelAttribute UpdateBookRequest request, Locale locale) {
        UpdateBookParam updateBookParam = UpdateBookParam.builder()
                .id(request.getId())
                .fileBook(request.getFileBook())
                .fileCoverImage(request.getFileCoverImage())
                .title(request.getTitle())
                .desc(request.getDesc())
                .totalPage(request.getTotalPage())
                .rating(request.getRating())
                .publishedYear(request.getPublishedYear())
                .build();
        Book book = bookService.updateBook(updateBookParam);
        BookResponse bookResponse = BookResponse.builder()
                .title(book.getTitle())
                .id(book.getId())
                .desc(book.getDescription())
                .rating(book.getRating())
                .publishedYear(book.getPublishedYear())
                .totalPage(book.getTotalPage())
                .fileUrl(book.getFileUrl())
                .build();

        String message = messageSource.getMessage("book.update.success", null, locale);
        return HTTPResponse.success(message, bookResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HTTPResponse> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        BookResponse bookResponse = BookResponse.builder()
                .title(book.getTitle())
                .id(book.getId())
                .desc(book.getDescription())
                .rating(book.getRating())
                .publishedYear(book.getPublishedYear())
                .totalPage(book.getTotalPage())
                .fileUrl(book.getFileUrl())
                .build();
        return HTTPResponse.success(bookResponse);
    }
    @GetMapping
    public ResponseEntity<HTTPResponse> getAllBook() {

        List<BookResponse> bookResponseList = bookService.getAllBook()
                .stream().map(book -> BookResponse.builder()
                        .title(book.getTitle())
                        .id(book.getId())
                        .desc(book.getDescription())
                        .rating(book.getRating())
                        .publishedYear(book.getPublishedYear())
                        .totalPage(book.getTotalPage())
                        .fileUrl(book.getFileUrl())
                        .build()).collect(Collectors.toList());

        return HTTPResponse.success(bookResponseList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HTTPResponse> deleteBook(@PathVariable Long id) {

        String message = "";
        if(bookService.deleteBook(id)){
            message = "Delete book successfully";
        }

        return HTTPResponse.success(message);
    }
}
