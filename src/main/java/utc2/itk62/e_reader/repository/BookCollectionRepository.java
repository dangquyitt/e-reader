package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import utc2.itk62.e_reader.domain.entity.BookCollection;

import java.util.Optional;

@Repository
public interface BookCollectionRepository extends JpaRepository<BookCollection, Long>, JpaSpecificationExecutor<BookCollection> {
    Optional<BookCollection> findByCollectionIdAndBookId(Long collectionId, Long bookId);
}
