package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utc2.itk62.e_reader.domain.entity.Author;
import utc2.itk62.e_reader.domain.entity.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findAllByAuthors(Author author);
}
