package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.domain.entity.BookTag;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.BookTagRepository;
import utc2.itk62.e_reader.service.BookTagService;

import java.util.Optional;

@AllArgsConstructor
@Service
public class BookTagServiceImpl implements BookTagService {
    private final BookTagRepository bookTagRepository;


    @Override
    public BookTag createBookTag(Long bookId, Long tagId) {
        Optional<BookTag> bookTag = bookTagRepository.findByBookIdAndTagId(bookId, tagId);
        if (bookTag.isPresent()) {
            throw new EReaderException("BookTag existed");
        }
        BookTag newBookTag = BookTag.builder()
                .tagId(tagId)
                .bookId(bookId)
                .build();
        return bookTagRepository.save(newBookTag);
    }

    @Override
    public void deleteTag(Long bookId, Long tagId) {
        Optional<BookTag> bookTag = bookTagRepository.findByBookIdAndTagId(bookId, tagId);
        bookTag.ifPresent(bookTagRepository::delete);
    }
}
