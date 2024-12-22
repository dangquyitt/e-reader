package utc2.itk62.e_reader.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.component.Translator;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Favorite;
import utc2.itk62.e_reader.domain.model.FavoriteFilter;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.dto.RequestFilter;
import utc2.itk62.e_reader.dto.favorite.CreateFavoriteRequest;
import utc2.itk62.e_reader.dto.favorite.DeleteFavoriteRequest;
import utc2.itk62.e_reader.service.FavoriteService;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/favorites")

@AllArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final MessageSource messageSource;
    private final Translator translator;


    @PostMapping
    public ResponseEntity<HTTPResponse> addFavorite(@Valid @RequestBody CreateFavoriteRequest createFavoriteRequest, Locale locale, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();

        Favorite favorite = favoriteService.addFavorite(tokenPayload.getUserId(), createFavoriteRequest.getBookId());

        String message = messageSource.getMessage("favorite.create.success", null, locale);
        return HTTPResponse.success(message, favorite);
    }

    @DeleteMapping
    public ResponseEntity<HTTPResponse> deleteFavorite(@Valid @RequestBody DeleteFavoriteRequest deleteFavoriteRequest, Locale locale, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();
        favoriteService.deleteByUserIdAndBookId(tokenPayload.getUserId(), deleteFavoriteRequest.getBookId());
        return HTTPResponse.success(translator.translate(locale, "favorite.delete.success"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HTTPResponse> deleteFavoriteById(@PathVariable("id") Long id, Locale locale, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();
        favoriteService.deleteFavoriteById(tokenPayload.getUserId(), id);
        return HTTPResponse.success(translator.translate(locale, "favorite.delete.success"));
    }


    @PostMapping("/filter")
    public ResponseEntity<HTTPResponse> getAllFavorites(@RequestBody RequestFilter<FavoriteFilter> filter, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();
        filter.getFilter().setUserId(tokenPayload.getUserId());
        List<Favorite> favorites = favoriteService.getAllFavorite(filter.getFilter(), filter.getPagination());
        return HTTPResponse.success("success", favorites, filter.getPagination());
    }
}
