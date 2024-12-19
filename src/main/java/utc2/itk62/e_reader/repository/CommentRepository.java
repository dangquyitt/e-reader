package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utc2.itk62.e_reader.domain.entity.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBookId(Long bookId);

    Optional<Comment> findByIdAndUserId(Long id, Long userId);

    List<Comment> findAllByUserId(Long userId);

    List<Comment> findAllByUserIdAndBookId(Long userId, Long bookId);
}
