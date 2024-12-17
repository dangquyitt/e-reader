package utc2.itk62.e_reader.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.entity.Comment;
import utc2.itk62.e_reader.domain.model.BookFilter;
import utc2.itk62.e_reader.domain.model.CreateBookParam;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.domain.model.UpdateBookParam;
import utc2.itk62.e_reader.dto.RequestFilter;
import utc2.itk62.e_reader.dto.book.BookResponse;
import utc2.itk62.e_reader.dto.book.CreateBookRequest;
import utc2.itk62.e_reader.dto.book.UpdateBookRequest;
import utc2.itk62.e_reader.dto.comment.CreateCommentRequest;
import utc2.itk62.e_reader.dto.comment.UpdateCommentRequest;
import utc2.itk62.e_reader.service.BookService;
import utc2.itk62.e_reader.service.impl.CommentService;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/comments")

@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final MessageSource messageSource;


    @PostMapping
    public ResponseEntity<HTTPResponse> create(@ModelAttribute CreateCommentRequest request, Locale locale, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();

        Comment comment = commentService.createComment(tokenPayload.getUserId(), request.getBookId(), request.getContent());

        String message = messageSource.getMessage("comment.create.success", null, locale);
        return HTTPResponse.success(message, comment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HTTPResponse> update(@ModelAttribute UpdateCommentRequest request, Locale locale, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();

        Comment comment = commentService.updateComment(tokenPayload.getUserId(), request.getId(), request.getContent());

        String message = messageSource.getMessage("comment.update.success", null, locale);
        return HTTPResponse.success(message, comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HTTPResponse> deleteComment(@PathVariable Long id) {

        String message = "";
        if (commentService.deleteComment(id)) {
            message = "Delete book successfully";
        }

        return HTTPResponse.success(message);
    }

    @GetMapping("/users")
    public ResponseEntity<HTTPResponse> getCommentByUser(Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();
        return HTTPResponse.success(commentService.getCommentByUser(tokenPayload.getUserId()));
    }
}
