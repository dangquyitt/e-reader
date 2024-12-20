package utc2.itk62.e_reader.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.component.Translator;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Rating;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.dto.rating.CreateRatingRequest;
import utc2.itk62.e_reader.dto.rating.DeleteRatingRequest;
import utc2.itk62.e_reader.dto.rating.GetRatingRequest;
import utc2.itk62.e_reader.dto.rating.UpdateRatingRequest;
import utc2.itk62.e_reader.service.RatingService;

import java.util.Locale;

@RestController
@RequestMapping("/api/ratings")

@AllArgsConstructor
public class RatingController {

    private final RatingService ratingService;
    private final MessageSource messageSource;
    private final Translator translator;


    @PostMapping
    public ResponseEntity<HTTPResponse> addRating(@Valid @RequestBody CreateRatingRequest createRatingRequest, Locale locale, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();

        Rating rating = ratingService.addRating(tokenPayload.getUserId(), createRatingRequest.getBookID(), createRatingRequest.getRating());

        String message = messageSource.getMessage("rating.create.success", null, locale);
        return HTTPResponse.success(message, rating);
    }

    @DeleteMapping
    public ResponseEntity<HTTPResponse> deleteRating(@Valid @RequestBody DeleteRatingRequest deleteRatingRequest, Locale locale, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();
        ratingService.deleteRating(deleteRatingRequest.getId(), tokenPayload.getUserId());
        return HTTPResponse.success(translator.translate(locale, "rating.delete.success"));
    }

    @PutMapping()
    public ResponseEntity<HTTPResponse> updateRating(@Valid @RequestBody UpdateRatingRequest updateRatingRequest, Locale locale, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();
        ratingService.updateRating(updateRatingRequest.getId(), tokenPayload.getUserId(), updateRatingRequest.getRating());
        return HTTPResponse.success(translator.translate(locale, "rating.update.success"));
    }

    @GetMapping()
    public ResponseEntity<HTTPResponse> getRating(@RequestBody GetRatingRequest getRatingRequest, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();
        getRatingRequest.setUserId(tokenPayload.getUserId());
        Rating rating = ratingService.getRating(getRatingRequest.getUserId(), getRatingRequest.getBookId());
        return HTTPResponse.success("success", rating);
    }
}
