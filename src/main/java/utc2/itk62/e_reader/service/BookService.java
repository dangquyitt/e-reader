package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.model.CreateBookParam;
import utc2.itk62.e_reader.domain.model.UpdateBookParam;

import java.util.List;

public interface BookService {

    Book createBook(CreateBookParam createBookParam);
    boolean deleteBook(Long id);
    Book updateBook(UpdateBookParam updateBookParam);
    Book getBook(Long id, Long userId);

    List<Book> getAllBook();
}
