package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.component.Translator;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.model.CreateBookParam;
import utc2.itk62.e_reader.domain.model.UpdateBookParam;

import utc2.itk62.e_reader.exception.NotFoundException;
import utc2.itk62.e_reader.repository.BookRepository;
import utc2.itk62.e_reader.service.BookService;
import utc2.itk62.e_reader.service.FileService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final Translator translator;
    private final BookRepository bookRepository;
    private final FileService fileService;

    @Override
    public Book createBook(CreateBookParam createBookParam) {

        Book book = Book.builder()
                .title(createBookParam.getTitle())
                .publishedYear(createBookParam.getPublishedYear())
                .genre(createBookParam.getGenre())
                .author(createBookParam.getAuthor())
                .totalPage(createBookParam.getTotalPage())
                .language(createBookParam.getLanguage())
                .fileUrl(fileService.uploadFile(createBookParam.getFile()))
                .build();

        return bookRepository.save(book);
    }

    @Override
    public boolean deleteBook(Long id) {
        var book = bookRepository.findById(id).orElseThrow(() -> {
            log.error("BookServiceImpl | id: {} not found", id);
            return new NotFoundException(translator.translate(MessageCode.BOOK_ID_NOT_FOUND));
        });
        fileService.deleteFile(book.getFileUrl());
        bookRepository.deleteById(id);
        return !bookRepository.existsById(id);
    }

    @Override
    public Book updateBook(UpdateBookParam updateBookParam) {
        Book book = bookRepository.findById(updateBookParam.getId()).orElseThrow(() -> {
            log.error("BookServiceImpl | id: {} not found", updateBookParam.getId());
            return new NotFoundException(translator.translate(MessageCode.BOOK_ID_NOT_FOUND));
        });
        fileService.deleteFile(book.getFileUrl());
        book.setFileUrl(fileService.uploadFile(updateBookParam.getFile()));
        book.setTitle(updateBookParam.getTitle());
        book.setGenre(updateBookParam.getGenre());
        book.setLanguage(updateBookParam.getLanguage());
        book.setAuthor(updateBookParam.getAuthor());
        book.setPublishedYear(updateBookParam.getPublishedYear());
        book.setTotalPage(updateBookParam.getTotalPage());
        return bookRepository.save(book);
    }

    @Override
    public Book getBook(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> {
            log.error("BookServiceImpl | id: {} not found", id);
            return new NotFoundException(translator.translate(MessageCode.BOOK_ID_NOT_FOUND));
        });
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }
}
