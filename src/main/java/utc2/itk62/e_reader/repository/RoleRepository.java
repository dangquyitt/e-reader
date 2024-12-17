package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import utc2.itk62.e_reader.domain.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(
            value = """
                    SELECT r.* FROM roles r
                    INNER JOIN user_roles ur ON r.id = ur.role_id
                    INNER JOIN users u ON ur.user_id = u.id
                    WHERE u.id = :userId
                    """,
            nativeQuery = true
    )
    List<Role> findAllByUserId(Long userId);

    List<Role> findAllByNameIn(List<String> names);

    Optional<Role> findByName(String name);
}
