package utc2.itk62.e_reader.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Author;
import utc2.itk62.e_reader.dto.AuthorResponse;
import utc2.itk62.e_reader.dto.CreateAuthorRequest;
import utc2.itk62.e_reader.dto.RoleResponse;
import utc2.itk62.e_reader.dto.UpdateAuthorRequest;
import utc2.itk62.e_reader.service.AuthorService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/authors")

@AllArgsConstructor
public class AuthorController {

    private final MessageSource messageSource;
    private final AuthorService authorService;



    @PostMapping
    public ResponseEntity<HTTPResponse> create(@RequestBody CreateAuthorRequest request, Locale locale) {
        Author author = authorService.createAuthor(request.getAuthorName());
        AuthorResponse authorResponse = AuthorResponse.builder()
                .id(author.getId())
                .authorName(author.getName())
                .build();

        String message = messageSource.getMessage("author.created", null, locale);
        return HTTPResponse.success(message,authorResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HTTPResponse> update(@RequestBody UpdateAuthorRequest request, Locale locale) {
        Author author = authorService.updateAuthor(request.getAuthorName(), request.getId());
        AuthorResponse authorResponse = AuthorResponse.builder()
                .id(author.getId())
                .authorName(author.getName())
                .build();

        String message = messageSource.getMessage("author.update.success", null, locale);
        return HTTPResponse.success(message, authorResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HTTPResponse> getRole(@PathVariable long id) {
        Author author = authorService.getAuthor(id);
        RoleResponse roleResponse = RoleResponse.builder()
                .id(author.getId())
                .roleName(author.getName())
                .build();
        return HTTPResponse.success(roleResponse);
    }
    @GetMapping
    public ResponseEntity<HTTPResponse> getAllRole() {

        List<AuthorResponse> authorResponses = authorService.getAllAuthor()
                .stream().map(author -> AuthorResponse.builder()
                        .id(author.getId())
                        .authorName(author.getName())
                        .build()).collect(Collectors.toList());

        return HTTPResponse.success(authorResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HTTPResponse> deleteRole(@PathVariable long id) {

        String message = "";
        if(authorService.deleteAuthor(id)){
            message = "Delete author successfully";
        }

        return HTTPResponse.success(message);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<HTTPResponse> getAuthorByBookId(@PathVariable long id){
        Author author = authorService.getAuthorByBookId(id);
        AuthorResponse authorResponse = AuthorResponse.builder()
                .id(author.getId())
                .authorName(author.getName())
                .build();
        return HTTPResponse.success(authorResponse);
    }
}
