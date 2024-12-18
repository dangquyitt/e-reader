package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.entity.Comment;
import utc2.itk62.e_reader.domain.entity.Favorite;

import java.util.List;

public interface FavoriteService {

    Favorite addFavorite(Long userId, Long bookId);

    List<Book> getAllBook(Long userId);

}
