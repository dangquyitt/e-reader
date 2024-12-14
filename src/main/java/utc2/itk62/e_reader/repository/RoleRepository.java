package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import utc2.itk62.e_reader.domain.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(
            value = """
                    select r from Role r
                    inner join r.userRoles ur
                    inner join ur.user u
                    where u.id = :userId
                    """
    )
    List<Role> findAllByUserId(Long userId);

    List<Role> findAllByNameIn(List<String> names);

    Optional<Role> findByName(String name);
}
