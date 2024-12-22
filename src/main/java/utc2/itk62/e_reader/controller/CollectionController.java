package utc2.itk62.e_reader.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.component.Translator;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Collection;
import utc2.itk62.e_reader.domain.model.CollectionFilter;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.dto.RequestFilter;
import utc2.itk62.e_reader.dto.collection.CreateCollectionRequest;
import utc2.itk62.e_reader.dto.collection.DeleteCollectionRequest;
import utc2.itk62.e_reader.service.CollectionService;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/collections")

@AllArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;
    private final MessageSource messageSource;
    private final Translator translator;


    @PostMapping
    public ResponseEntity<HTTPResponse> addCollection(@Valid @RequestBody CreateCollectionRequest createCollectionRequest, Locale locale, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();

        Collection collection = collectionService.addCollection(tokenPayload.getUserId(), createCollectionRequest.getName());

        String message = messageSource.getMessage("collection.create.success", null, locale);
        return HTTPResponse.success(message, collection);
    }

    @DeleteMapping
    public ResponseEntity<HTTPResponse> deleteCollection(@Valid @RequestBody DeleteCollectionRequest deleteCollectionRequest, Locale locale, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();
        collectionService.deleteCollection(deleteCollectionRequest.getId(), tokenPayload.getUserId());
        return HTTPResponse.success(translator.translate(locale, "collection.delete.success"));
    }

    @PostMapping("/filter")
    public ResponseEntity<HTTPResponse> getAllCollection(@RequestBody RequestFilter<CollectionFilter> filter, Authentication authentication) {
        TokenPayload tokenPayload = (TokenPayload) authentication.getPrincipal();
        filter.getFilter().setUserId(tokenPayload.getUserId());
        List<Collection> collections = collectionService.getAllCollection(filter.getFilter(), filter.getPagination());
        return HTTPResponse.success("success", collections, filter.getPagination());
    }
}
