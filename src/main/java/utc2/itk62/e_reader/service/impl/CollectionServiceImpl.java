package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Collection;
import utc2.itk62.e_reader.domain.model.CollectionFilter;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.CollectionRepository;
import utc2.itk62.e_reader.service.CollectionService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CollectionServiceImpl implements CollectionService {
    private final CollectionRepository collectionRepository;

    @Override
    public Collection addCollection(Long userId, String name) {
        Optional<Collection> optionalCollection = collectionRepository.findByUserIdAndName(userId, name);

        if (optionalCollection.isPresent()) {
            throw new EReaderException("The collection already exists");
        } else {
            Collection newCollection = new Collection();
            newCollection.setUserId(userId);
            newCollection.setName(name);
            return collectionRepository.save(newCollection);
        }
    }

    @Override
    public List<Collection> getAllCollection(CollectionFilter collectionFilter, Pagination pagination) {
        Specification<Collection> spec = Specification.where(null);

        if (collectionFilter != null) {
            if (collectionFilter.getUserId() != null) {
                spec = spec.and(((root, query, criteriaBuilder) -> criteriaBuilder.
                        equal(root.get("userId"), collectionFilter.getUserId())));
            }
            if (collectionFilter.getName() != null) {
                String namePattern = "%" + collectionFilter.getName() + "%";
                spec = spec.and(((root, query, cb) -> cb.like(root.get("name"), namePattern)));
            }
        }

        Pageable pageable = PageRequest.of(pagination.getPage() - 1, pagination.getPageSize());
        Page<Collection> pageCollection = collectionRepository.findAll(spec, pageable);
        pagination.setTotal(pageCollection.getTotalPages());
        return pageCollection.toList();
    }

    @Override
    public void deleteCollection(Long id, Long userId) {
        Optional<Collection> collection = collectionRepository.findByIdAndUserId(userId, userId);
        collection.ifPresent(collectionRepository::delete);
    }
}
