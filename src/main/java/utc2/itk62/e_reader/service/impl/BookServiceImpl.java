package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                .description(createBookParam.getDesc())
                .rating(createBookParam.getRating())
                .publishedYear(createBookParam.getPublishedYear())
                .totalPage(createBookParam.getTotalPage())
                .fileUrl(fileService.uploadFile(createBookParam.getFileBook()))
                .coverImageUrl(fileService.uploadFile(createBookParam.getFileCoverImage()))
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
        fileService.deleteFile(book.getCoverImageUrl());
        book.setFileUrl(fileService.uploadFile(updateBookParam.getFileBook()));
        book.setCoverImageUrl(fileService.uploadFile(updateBookParam.getFileCoverImage()));
        book.setTitle(updateBookParam.getTitle());
        book.setDescription(updateBookParam.getDesc());
        book.setRating(updateBookParam.getRating());
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
    public Page<Book> getAllBook(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page,pageSize);
        return bookRepository.findAll(pageable);
    }
}
