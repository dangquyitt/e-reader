package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.model.BookFilter;
import utc2.itk62.e_reader.domain.model.CreateBookParam;
import utc2.itk62.e_reader.domain.model.OrderBy;
import utc2.itk62.e_reader.domain.model.UpdateBookParam;
import utc2.itk62.e_reader.dto.book.BookDetail;

import java.util.List;

public interface BookService {
    Book createBook(CreateBookParam createBookParam);

    boolean deleteBook(Long id);

    Book updateBook(UpdateBookParam updateBookParam);

    Book getBook(Long id);

    List<Book> getAllBook(BookFilter bookFilter, OrderBy orderBy, Pagination pagination);

    BookDetail getBookDetail(long bookId, long userId);

    List<Book> getBooksByCollectionIds(List<Long> collectionIds, Pagination pagination);

    List<Book> getBooksByAuthorId(Long authorId);
}
