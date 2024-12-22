package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.entity.BookAuthor;
import utc2.itk62.e_reader.domain.model.BookFilter;
import utc2.itk62.e_reader.domain.model.CreateBookParam;
import utc2.itk62.e_reader.domain.model.OrderBy;
import utc2.itk62.e_reader.domain.model.UpdateBookParam;
import utc2.itk62.e_reader.dto.book.BookDetail;

import java.util.List;

public interface BookAuthorService {

    BookAuthor createBookAuthor(Long bookId, Long authorId);

    void deleteBookAuthor(Long bookId, Long authorId);

}
