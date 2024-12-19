package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.entity.Comment;
import utc2.itk62.e_reader.domain.entity.Favorite;
import utc2.itk62.e_reader.domain.model.BookFilter;
import utc2.itk62.e_reader.domain.model.FavoriteFilter;

import java.util.List;

public interface FavoriteService {

    Favorite addFavorite(Long userId, Long bookId);

    List<Favorite> getAllFavorite(FavoriteFilter favoriteFilter, Pagination pagination);

    void deleteByUserIdAndBookId(Long userId, Long bookId);
}
