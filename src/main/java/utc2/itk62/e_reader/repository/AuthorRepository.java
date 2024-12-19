package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import utc2.itk62.e_reader.domain.entity.Author;
import utc2.itk62.e_reader.domain.entity.Book;

public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
    @Query(value = "SELECT a.* FROM authors a " +
            "JOIN book_authors ba ON a.id = ba.authors_id " +
            "WHERE ba.book_id = :bookId",
            nativeQuery = true)
    Author findAuthorByBookId(@Param("bookId") Long bookId);

}
