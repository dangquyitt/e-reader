package utc2.itk62.e_reader.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Comment;
import utc2.itk62.e_reader.domain.entity.Favorite;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.dto.comment.CreateCommentRequest;
import utc2.itk62.e_reader.dto.comment.UpdateCommentRequest;
import utc2.itk62.e_reader.service.CommentService;
import utc2.itk62.e_reader.service.FavoriteService;

import java.util.Locale;

@RestController
@RequestMapping("/api/favorites")

@AllArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final MessageSource messageSource;


    @PostMapping("{bookID}")
    public ResponseEntity<HTTPResponse> addFavorite(@PathVariable Long bookID, Locale locale, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();

        Favorite favorite = favoriteService.addFavorite(tokenPayload.getUserId(), bookID);

        String message = messageSource.getMessage("favorite.create.success", null, locale);
        return HTTPResponse.success(message, favorite);
    }


    @GetMapping()
    public ResponseEntity<HTTPResponse> getFavoriteByUser(Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();
        return HTTPResponse.success(favoriteService.getAllBook(tokenPayload.getUserId()));
    }
}
