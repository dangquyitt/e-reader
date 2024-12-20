package utc2.itk62.e_reader.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.component.Translator;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.BookCollection;
import utc2.itk62.e_reader.domain.entity.Collection;
import utc2.itk62.e_reader.domain.model.BookCollectionFilter;
import utc2.itk62.e_reader.domain.model.CollectionFilter;
import utc2.itk62.e_reader.domain.model.TokenPayload;
import utc2.itk62.e_reader.dto.RequestFilter;
import utc2.itk62.e_reader.dto.bookcollection.CreateBookCollectionRequest;
import utc2.itk62.e_reader.dto.bookcollection.DeleteBookCollectionRequest;
import utc2.itk62.e_reader.dto.collection.CreateCollectionRequest;
import utc2.itk62.e_reader.dto.collection.DeleteCollectionRequest;
import utc2.itk62.e_reader.service.BookCollectionService;
import utc2.itk62.e_reader.service.CollectionService;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/bookcollections")

@AllArgsConstructor
public class BookCollectionController {

    private final CollectionService collectionService;
    private final BookCollectionService bookCollectionService;
    private final MessageSource messageSource;
    private final Translator translator;


    @PostMapping
    public ResponseEntity<HTTPResponse> addBookCollection(@Valid @RequestBody
                                                          CreateBookCollectionRequest createCollectionRequest,
                                                          Locale locale) {
        BookCollection bookCollection = bookCollectionService
                .addBookCollection(createCollectionRequest.getCollectionId(), createCollectionRequest.getBookId());

        String message = messageSource.getMessage("bookcollection.create.success", null, locale);
        return HTTPResponse.success(message, bookCollection);
    }

    @DeleteMapping
    public ResponseEntity<HTTPResponse> deleteCollection(@Valid @RequestBody
                                                         DeleteBookCollectionRequest deleteBookCollectionRequest,
                                                         Locale locale) {
        bookCollectionService
                .deleteBookCollection(deleteBookCollectionRequest
                        .getCollectionId(), deleteBookCollectionRequest.getBookId());
        return HTTPResponse.success(translator.translate(locale, "bookcollection.delete.success"));
    }


    @PostMapping("/filter")
    public ResponseEntity<HTTPResponse> getAllCollection(@RequestBody
                                                         RequestFilter<BookCollectionFilter> filter) {
        List<BookCollection> bookCollections = bookCollectionService
                .getAllBookCollection(filter.getFilter(), filter.getPagination());
        return HTTPResponse.success("success", bookCollections, filter.getPagination());
    }
}
