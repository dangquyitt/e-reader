package utc2.itk62.e_reader.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Tag;
import utc2.itk62.e_reader.domain.model.TagFilter;
import utc2.itk62.e_reader.dto.RequestFilter;
import utc2.itk62.e_reader.dto.tag.CreateTagRequest;
import utc2.itk62.e_reader.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/tags")

@AllArgsConstructor
public class TagController {

    private final TagService tagService;


    @PostMapping
    public ResponseEntity<HTTPResponse> addTag(@Valid @RequestBody CreateTagRequest createTagRequest) {

        Tag tag = tagService.createTag(createTagRequest.getName());

        return HTTPResponse.success("Tag create success", tag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HTTPResponse> deleteTag(@Valid @PathVariable Long id) {
        tagService.deleteTag(id);
        return HTTPResponse.success("tag delete success");
    }


    @PostMapping("/filter")
    public ResponseEntity<HTTPResponse> getAllCollection(@RequestBody RequestFilter<TagFilter> filter) {
        List<Tag> tags = tagService.getAllTag(filter.getFilter(), filter.getPagination());
        return HTTPResponse.success("success", tags, filter.getPagination());
    }
}
