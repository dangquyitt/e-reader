package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Author;
import utc2.itk62.e_reader.domain.entity.Role;
import utc2.itk62.e_reader.domain.model.AuthorFilter;

import java.util.List;

public interface AuthorService {

    Author createAuthor(String author);

    boolean deleteAuthor(long id);

    Author updateAuthor(String author, long id);

    Author getAuthor(long id);

    List<Author> getAllAuthor(AuthorFilter authorFilter, Pagination pagination);

    Author getAuthorByBookId(long bookId);
}
