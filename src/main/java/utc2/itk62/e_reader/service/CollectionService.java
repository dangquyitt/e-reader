package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Collection;
import utc2.itk62.e_reader.domain.model.CollectionFilter;
import utc2.itk62.e_reader.domain.model.FavoriteFilter;

import java.util.List;

public interface CollectionService {

    Collection addCollection(Long userId, String name);

    List<Collection> getAllCollection(CollectionFilter collectionFilter, Pagination pagination);

    void deleteCollection(Long id, Long userId);
}
