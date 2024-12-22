package utc2.itk62.e_reader.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.BookAuthor;
import utc2.itk62.e_reader.dto.book_author.CreateBookAuthorRequest;
import utc2.itk62.e_reader.dto.book_author.DeleteBookAuthorRequest;
import utc2.itk62.e_reader.service.BookAuthorService;

import java.util.Locale;

@RestController
@RequestMapping("/api/bookAuthors")
@AllArgsConstructor
public class BookAuthorController {

    private final BookAuthorService bookAuthorService;


    @PostMapping
    public ResponseEntity<HTTPResponse> create(@RequestBody CreateBookAuthorRequest request, Locale locale) {
        BookAuthor bookAuthor = bookAuthorService.createBookAuthor(request.getBookId(), request.getAuthorId());

        return HTTPResponse.success("BookAuthor create success", bookAuthor);
    }


    @DeleteMapping()
    public ResponseEntity<HTTPResponse> deleteBook(@RequestBody DeleteBookAuthorRequest request) {

        bookAuthorService.deleteBookAuthor(request.getBookId(), request.getAuthorId());
        return HTTPResponse.success("BookAuthor delete success");
    }


}
