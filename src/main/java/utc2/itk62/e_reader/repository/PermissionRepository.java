package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utc2.itk62.e_reader.domain.entity.Permission;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query(
            value = """
                    SELECT EXISTS(
                        SELECT 1
                        FROM permissions p
                        LEFT JOIN role_permissions rp ON rp.permission_id = p.id
                        LEFT JOIN roles r ON r.id = rp.role_id
                        LEFT JOIN user_roles ur ON ur.role_id = r.id
                        LEFT JOIN users u ON u.id = ur.user_id
                    WHERE
                        u.id = :userId
                        AND p.http_method = :httpMethod
                        AND :path ~ p.path
                    )
                    """,
            nativeQuery = true
    )
    boolean existsPermission(Long userId, String httpMethod, String path);
}
