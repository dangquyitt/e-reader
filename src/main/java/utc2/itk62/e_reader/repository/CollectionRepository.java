package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.entity.Collection;
import utc2.itk62.e_reader.domain.entity.Favorite;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long>, JpaSpecificationExecutor<Collection> {
    Optional<Collection> findByUserIdAndName(Long userId, String name);

    Optional<Collection> findByIdAndUserId(Long id, Long userId);

    List<Collection> findAllByUserId(Long userId);

    @Query(
            value = """
                        SELECT c.* FROM collections c
                        INNER JOIN book_collections bc ON bc.collection_id = c.id
                        WHERE c.user_id = :userId AND bc.book_id = :bookId
                    """,
            nativeQuery = true
    )
    List<Collection> findAllByUserIdAndBookId(Long userId, Long bookId);

}
