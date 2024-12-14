package utc2.itk62.e_reader.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.model.BookFilter;
import utc2.itk62.e_reader.domain.model.CreateBookParam;
import utc2.itk62.e_reader.domain.model.UpdateBookParam;
import utc2.itk62.e_reader.dto.book.BookResponse;
import utc2.itk62.e_reader.dto.book.CreateBookRequest;
import utc2.itk62.e_reader.dto.RequestFilter;
import utc2.itk62.e_reader.dto.book.UpdateBookRequest;
import utc2.itk62.e_reader.service.BookService;

import java.util.List;
import java.util.Locale;

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
        BookResponse bookResponse = new BookResponse(book);

        String message = messageSource.getMessage("book.create.success", null, locale);
        return HTTPResponse.success(message, bookResponse);
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
        BookResponse bookResponse = new BookResponse(book);

        String message = messageSource.getMessage("book.update.success", null, locale);
        return HTTPResponse.success(message, bookResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HTTPResponse> getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);
        BookResponse bookResponse = new BookResponse(book);
        return HTTPResponse.success(bookResponse);
    }

    @PostMapping("/filter")
    public ResponseEntity<HTTPResponse> getAllBook(@RequestBody RequestFilter<BookFilter> filter) {
        List<BookResponse> resp = bookService.
                getAllBook(filter.getFilter(), filter.getPagination()).stream().map(BookResponse::new).toList();
        return HTTPResponse.success("Books retrieved successfully", resp, filter.getPagination());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HTTPResponse> deleteBook(@PathVariable Long id) {

        String message = "";
        if (bookService.deleteBook(id)) {
            message = "Delete book successfully";
        }

        return HTTPResponse.success(message);
    }
}
