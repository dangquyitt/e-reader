package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.BookTag;
import utc2.itk62.e_reader.domain.entity.Tag;
import utc2.itk62.e_reader.domain.model.TagFilter;

import java.util.List;

public interface BookTagService {

    BookTag createBookTag(Long bookId, Long tagId);

    void deleteTag(Long bookId, Long tagId);


}
