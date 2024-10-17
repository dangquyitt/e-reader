package utc2.itk62.e_reader.service;

import org.springframework.web.multipart.MultipartFile;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.dto.CreateBookRequest;

import java.util.List;

public interface BookService {

    Book createBook(MultipartFile file, CreateBookRequest request);
    boolean deleteBook(Long id);
    Book updateBook(MultipartFile file, CreateBookRequest request, Long id);

    Book getBook(Long id);
    List<Book> getAllBook();
}
