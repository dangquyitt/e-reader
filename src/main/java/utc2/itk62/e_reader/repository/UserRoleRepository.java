package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utc2.itk62.e_reader.domain.entity.Role;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.domain.entity.UserRole;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    boolean existsByUserIdAndRoleId(Long userId, Long roleId);

    List<UserRole> findByUserId(Long userId);
}
