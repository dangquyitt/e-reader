package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.component.Translator;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Author;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.model.AuthorFilter;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.AuthorRepository;
import utc2.itk62.e_reader.repository.BookRepository;
import utc2.itk62.e_reader.service.AuthorService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final Translator translator;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    public Author createAuthor(String authorName) {
        Author author = Author.builder()
                .name(authorName)
                .build();
        return authorRepository.save(author);
    }

    @Override
    public boolean deleteAuthor(long id) {
        var author = authorRepository.findById(id).orElseThrow(() -> {
            log.error("AuthorServiceImpl | id: {} not found", id);
            return new EReaderException(MessageCode.AUTHOR_ID_NOT_FOUND);
        });
        authorRepository.delete(author);
        return !authorRepository.existsById(id);
    }

    @Override
    public Author updateAuthor(String authorName, long id) {
        var author = authorRepository.findById(id).orElseThrow(() -> {
            log.error("AuthorServiceImpl | id: {} not found", id);
            return new EReaderException(MessageCode.AUTHOR_ID_NOT_FOUND);
        });
        author.setName(authorName);
        return authorRepository.save(author);
    }

    @Override
    public Author getAuthor(long id) {
        return authorRepository.findById(id).orElseThrow(() -> {
            log.error("AuthorServiceImpl | id: {} not found", id);
            return new EReaderException(MessageCode.AUTHOR_ID_NOT_FOUND);
        });
    }

    @Override
    public List<Author> getAllAuthor(AuthorFilter authorFilter, Pagination pagination) {
        Pageable pageable = PageRequest.of(pagination.getPage() - 1, pagination.getPageSize());
        Page<Author> authors = authorRepository.findAll(pageable);
        pagination.setTotal(authors.getTotalPages());
        return authors.toList();
    }

    @Override
    public Author getAuthorByBookId(long bookId) {
        var book = bookRepository.findById(bookId).orElseThrow(() ->
                new EReaderException(MessageCode.BOOK_ID_NOT_FOUND)
        );
        try {
            return authorRepository.findAuthorByBookId(book.getId());
        } catch (Exception e) {
            throw new EReaderException(MessageCode.AUTHOR_BOOK_ID_NOT_FOUND);
        }
    }
}
