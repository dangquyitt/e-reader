package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utc2.itk62.e_reader.domain.entity.Book;

import java.util.List;
import java.util.Optional;


@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Override
    @Query(value = """
                update Book b set b.deletedAt = CURRENT_TIMESTAMP where b.id = :id
            """)
    @Modifying
    void deleteById(Long id);

    @Query("SELECT b FROM Book b WHERE b.id = (SELECT r.bookId FROM Rating r WHERE r.id = :ratingId)")
    Optional<Book> findBookByRatingId(Long ratingId);

    @Query(
            value = """
                    SELECT b.*
                    FROM books b
                    INNER JOIN book_collections bc ON b.id = bc.book_id
                    WHERE bc.collection_id IN :collectionIds
                    LIMIT :limit OFFSET :offset
                    """,
            nativeQuery = true

    )
    List<Book> findBooksByCollectionIds(List<Long> collectionIds, int limit, int offset);

    @Query(
            value = """
                    SELECT COUNT(DISTINCT b.id)
                    FROM books b
                    INNER JOIN book_collections bc ON b.id = bc.book_id
                    WHERE bc.collection_id IN :collectionIds
                    """,
            nativeQuery = true
    )
    long countByCollectionIds(List<Long> collectionIds);

    @Query(
            value = """
                    SELECT b.*
                    FROM books b
                    INNER JOIN book_author ba ON b.id = ba.book_id
                    WHERE ba.author_id IN :authorId
                    """,
            nativeQuery = true

    )
    List<Book> findBooksByAuthorId(Long authorId);
}
