package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.BookCollection;
import utc2.itk62.e_reader.domain.model.BookCollectionFilter;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.BookCollectionRepository;
import utc2.itk62.e_reader.service.BookCollectionService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookCollectionServiceImpl implements BookCollectionService {
    private final BookCollectionRepository bookCollectionRepository;

    @Override
    public BookCollection addBookCollection(Long collectionId, Long bookId) {
        Optional<BookCollection> bookCollection = bookCollectionRepository.findByCollectionIdAndBookId(collectionId, bookId);

        if (bookCollection.isPresent()) {
            throw new EReaderException("The collection already exists");
        } else {
            BookCollection newBookCollection = new BookCollection();
            newBookCollection.setCollectionId(collectionId);
            newBookCollection.setBookId(bookId);
            return bookCollectionRepository.save(newBookCollection);
        }
    }

    @Override
    public List<BookCollection> getAllBookCollection(BookCollectionFilter bookCollectionFilter, Pagination pagination) {
        Specification<BookCollection> spec = Specification.where(null);
        if (!CollectionUtils.isEmpty(bookCollectionFilter.getIds())) {
            spec = spec.and(((root, query, cb) -> root.get("id").in(bookCollectionFilter.getIds())));
        }
        if (!CollectionUtils.isEmpty(bookCollectionFilter.getCollectionIds())) {
            spec = spec.and(((root, query, cb) -> root.get("collectionId").in(bookCollectionFilter.getCollectionIds())));
        }

        Pageable pageable = PageRequest.of(pagination.getPage() - 1, pagination.getPageSize());
        Page<BookCollection> pageBookCollections = bookCollectionRepository.findAll(spec, pageable);
        pagination.setTotal(pageBookCollections.getTotalElements());
        return pageBookCollections.toList();
    }

    @Override
    public void deleteBookCollection(Long collectionId, Long bookId) {
        Optional<BookCollection> bookCollection = bookCollectionRepository.findByCollectionIdAndBookId(collectionId, bookId);
        bookCollection.ifPresent(bookCollectionRepository::delete);
    }
}
