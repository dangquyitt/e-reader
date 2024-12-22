package utc2.itk62.e_reader.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.BookAuthor;
import utc2.itk62.e_reader.domain.entity.BookTag;
import utc2.itk62.e_reader.dto.book_author.CreateBookAuthorRequest;
import utc2.itk62.e_reader.dto.book_author.DeleteBookAuthorRequest;
import utc2.itk62.e_reader.dto.book_tag.CreateBookTagRequest;
import utc2.itk62.e_reader.dto.book_tag.DeleteBookTagRequest;
import utc2.itk62.e_reader.service.BookAuthorService;
import utc2.itk62.e_reader.service.BookTagService;

import java.util.Locale;

@RestController
@RequestMapping("/api/bookTags")

@AllArgsConstructor
public class BookTagController {

    private final BookAuthorService bookAuthorService;
    private final BookTagService bookTagService;


    @PostMapping
    public ResponseEntity<HTTPResponse> create(@RequestBody CreateBookTagRequest request) {
        BookTag bookTag = bookTagService.createBookTag(request.getBookId(), request.getTagId());

        return HTTPResponse.success("BookAuthor create success", bookTag);
    }


    @DeleteMapping()
    public ResponseEntity<HTTPResponse> deleteBook(@RequestBody DeleteBookTagRequest request) {

        bookAuthorService.deleteBookAuthor(request.getBookId(), request.getTagId());
        return HTTPResponse.success("BookAuthor delete success");
    }


}
