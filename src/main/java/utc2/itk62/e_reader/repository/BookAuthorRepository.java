package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import utc2.itk62.e_reader.domain.entity.BookAuthor;

import java.util.Optional;


@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long>, JpaSpecificationExecutor<BookAuthor> {

    Optional<BookAuthor> findByAuthorIdAndBookId(Long authorId, Long bookId);
}
