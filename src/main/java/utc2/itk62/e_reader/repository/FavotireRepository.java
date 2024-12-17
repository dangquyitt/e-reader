package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.entity.Favorite;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavotireRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserIdAndBookId(Long userId, Long bookId);

    @Query("SELECT b FROM Book b " +
            "JOIN Favorite f ON f.bookId = b.id " +
            "WHERE f.userId = :userId")
    List<Book> findBooksByUserId(Long userId);
}
