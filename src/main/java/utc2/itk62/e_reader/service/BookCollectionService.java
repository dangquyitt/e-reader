package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.BookCollection;
import utc2.itk62.e_reader.domain.entity.Collection;
import utc2.itk62.e_reader.domain.model.BookCollectionFilter;
import utc2.itk62.e_reader.domain.model.CollectionFilter;

import java.util.List;

public interface BookCollectionService {

    BookCollection addBookCollection(Long collectionId, Long bookId);

    List<BookCollection> getAllBookCollection(BookCollectionFilter bookCollectionFilter, Pagination pagination);

    void deleteBookCollection(Long collectionId, Long bookId);
}
