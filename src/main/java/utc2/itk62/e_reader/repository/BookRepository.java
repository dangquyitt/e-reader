package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utc2.itk62.e_reader.domain.entity.Book;

public interface BookRepository extends JpaRepository<Book,Long> {
}
