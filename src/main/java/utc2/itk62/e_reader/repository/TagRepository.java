package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utc2.itk62.e_reader.domain.entity.BookAuthor;
import utc2.itk62.e_reader.domain.entity.Tag;

import java.util.List;
import java.util.Optional;


@Repository
public interface TagRepository extends JpaRepository<Tag, Long>, JpaSpecificationExecutor<Tag> {

    @Query(
            value = """
                    SELECT t.*
                    FROM tags t
                    INNER JOIN book_tags bt ON t.id = bt.tag_id
                    WHERE bt.book_id IN :bookId
                         """,
            nativeQuery = true

    )
    List<Tag> findAllByBookId(Long bookId);
}
