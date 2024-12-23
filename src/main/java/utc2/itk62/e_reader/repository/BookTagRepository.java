package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utc2.itk62.e_reader.domain.entity.BookTag;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public interface BookTagRepository extends JpaRepository<BookTag, Long>, JpaSpecificationExecutor<BookTag> {

    Optional<BookTag> findByBookIdAndTagId(Long bookId, Long tagId);
}
