package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utc2.itk62.e_reader.domain.entity.Permission;
import utc2.itk62.e_reader.domain.entity.Role;
import utc2.itk62.e_reader.domain.enums.RoleName;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    boolean existsByPath(String path);
}
