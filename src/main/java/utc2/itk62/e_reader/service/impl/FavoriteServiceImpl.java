package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.entity.Favorite;
import utc2.itk62.e_reader.repository.FavotireRepository;
import utc2.itk62.e_reader.service.FavoriteService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FavoriteServiceImpl implements FavoriteService {
    private final FavotireRepository favotireRepository;


    @Override
    public Favorite addFavorite(Long userId, Long bookId) {
        Optional<Favorite> optionalFavorite = favotireRepository.findByUserIdAndBookId(userId, bookId);

        if (optionalFavorite.isPresent()) {
            favotireRepository.delete(optionalFavorite.get());
            return null;
        } else {
            Favorite newFavorite = new Favorite();
            newFavorite.setUserId(userId);
            newFavorite.setBookId(bookId);
            return favotireRepository.save(newFavorite);
        }
    }

    @Override
    public List<Book> getAllBook(Long userId) {
        return favotireRepository.findBooksByUserId(userId);
    }
}
