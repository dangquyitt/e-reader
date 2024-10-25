package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.domain.entity.UserBookAccess;

import java.time.Instant;
import java.util.List;

public interface UserBookAccessRepository extends JpaRepository<UserBookAccess,Long> {
    List<UserBookAccess> findAllByUserAndAccessDateBetween(User user, Instant startDate, Instant endDate);
    boolean existsByUserId(Long userId);
    UserBookAccess findByUserId(Long userId);
}
