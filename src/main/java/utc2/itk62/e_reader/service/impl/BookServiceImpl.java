package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.model.CreateBookParam;
import utc2.itk62.e_reader.domain.model.UpdateBookParam;
import utc2.itk62.e_reader.exception.CustomException;
import utc2.itk62.e_reader.repository.BookRepository;
import utc2.itk62.e_reader.service.BookService;
import utc2.itk62.e_reader.service.FileService;

import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

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
        var book = bookRepository.findById(id).orElseThrow(() ->
                new CustomException().addError(new Error("id","book.id.not_found")));
        fileService.deleteFile(book.getFileUrl());
        bookRepository.delete(book);
        return !bookRepository.existsById(id);
    }

    @Override
    public Book updateBook(UpdateBookParam updateBookParam) {
        Book book = bookRepository.findById(updateBookParam.getId()).orElseThrow(() ->
                new CustomException().addError(new Error("id","book.id.not_found")));
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
        return bookRepository.findById(id).orElseThrow(() ->
                new CustomException().addError(new Error("id","book.id.not_found")));
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }
}
