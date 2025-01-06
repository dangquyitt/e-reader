package utc2.itk62.e_reader.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Comment;
import utc2.itk62.e_reader.domain.model.CommentFilter;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.dto.RequestFilter;
import utc2.itk62.e_reader.dto.comment.CreateCommentRequest;
import utc2.itk62.e_reader.dto.comment.UpdateCommentRequest;
import utc2.itk62.e_reader.service.CommentService;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/comments")

@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final MessageSource messageSource;


    @GetMapping("/{id}")
    public ResponseEntity<HTTPResponse> getComment(@PathVariable Long id, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();
        Comment comment = commentService.getCommentById(tokenPayload.getUserId(), id);
        return HTTPResponse.success("success", comment);
    }

    @PostMapping
    public ResponseEntity<HTTPResponse> create(@RequestBody CreateCommentRequest request, Locale locale, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();

        Comment comment = commentService.createComment(tokenPayload.getUserId(), request.getBookId(), request.getContent());

        String message = messageSource.getMessage("comment.create.success", null, locale);
        return HTTPResponse.success(message, comment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HTTPResponse> update(@RequestBody UpdateCommentRequest request, Locale locale, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();

        Comment comment = commentService.updateComment(tokenPayload.getUserId(), request.getId(), request.getContent());

        String message = messageSource.getMessage("comment.update.success", null, locale);
        return HTTPResponse.success(message, comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HTTPResponse> deleteComment(@PathVariable Long id, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();
        String message = "";
        if (commentService.deleteComment(id, tokenPayload.getUserId())) {
            message = "Delete book successfully";
        }
        return HTTPResponse.success(message);
    }

    @PostMapping("/filter")
    public ResponseEntity<HTTPResponse> getCommentByUser(@RequestBody RequestFilter<CommentFilter> filter, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();
        filter.getFilter().setUserId(tokenPayload.getUserId());
        List<Comment> resp = commentService.
                getCommentByUser(filter.getFilter(), filter.getOrderBy(), filter.getPagination());
        return HTTPResponse.success("Comment retrieved successfully", resp, filter.getPagination());
    }
}
