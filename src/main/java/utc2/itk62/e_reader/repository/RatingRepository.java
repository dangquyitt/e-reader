package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import utc2.itk62.e_reader.domain.entity.Rating;

import java.util.List;
import java.util.Optional;


@Repository
public interface RatingRepository extends JpaRepository<Rating, Long>, JpaSpecificationExecutor<Rating> {
    Optional<Rating> findByUserIdAndBookId(Long userId, Long bookId);

    Optional<Rating> findByIdAndUserId(Long id, Long userId);

    List<Rating> findAllByBookId(Long bookId);

}
