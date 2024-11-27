package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
