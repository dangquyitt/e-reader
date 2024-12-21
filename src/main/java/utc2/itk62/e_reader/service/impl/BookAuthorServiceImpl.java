package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.domain.entity.BookAuthor;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.BookAuthorRepository;
import utc2.itk62.e_reader.service.BookAuthorService;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class BookAuthorServiceImpl implements BookAuthorService {
    private final BookAuthorRepository bookAuthorRepository;

    @Override
    public BookAuthor createBookAuthor(Long bookId, Long authorId) {
        Optional<BookAuthor> bookAuthor = bookAuthorRepository.findByAuthorIdAndBookId(authorId, bookId);
        if (bookAuthor.isPresent()) {
            throw new EReaderException("BookAuthor existed");
        }
        BookAuthor newBookAuthor = BookAuthor.builder()
                .authorId(authorId)
                .bookId(bookId)
                .build();
        return bookAuthorRepository.save(newBookAuthor);
    }

    @Override
    public void deleteBookAuthor(Long bookId, Long authorId) {
        Optional<BookAuthor> bookAuthor = bookAuthorRepository.findByAuthorIdAndBookId(authorId, bookId);
        bookAuthor.ifPresent(bookAuthorRepository::delete);
    }
}
