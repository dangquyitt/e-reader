package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Tag;
import utc2.itk62.e_reader.domain.model.TagFilter;

import java.util.List;

public interface TagService {

    Tag createTag(String name);

    void deleteTag(Long id);

    List<Tag> getAllTag(TagFilter tagFilter, Pagination pagination);

}
