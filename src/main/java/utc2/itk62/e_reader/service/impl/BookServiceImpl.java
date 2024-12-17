package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.entity.Comment;
import utc2.itk62.e_reader.domain.model.BookFilter;
import utc2.itk62.e_reader.domain.model.CreateBookParam;
import utc2.itk62.e_reader.domain.model.UpdateBookParam;

import utc2.itk62.e_reader.dto.book.BookDetail;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.BookRepository;
import utc2.itk62.e_reader.repository.CommentRepository;
import utc2.itk62.e_reader.service.BookService;
import utc2.itk62.e_reader.service.FileService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final FileService fileService;
    private final CommentRepository commentRepository;

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
            return new EReaderException(MessageCode.BOOK_ID_NOT_FOUND);
        });
        fileService.deleteFile(book.getFileUrl());
        bookRepository.deleteById(id);
        return !bookRepository.existsById(id);
    }

    @Override
    public Book updateBook(UpdateBookParam updateBookParam) {
        Book book = bookRepository.findById(updateBookParam.getId()).orElseThrow(() -> {
            log.error("BookServiceImpl | id: {} not found", updateBookParam.getId());
            return new EReaderException(MessageCode.BOOK_ID_NOT_FOUND);
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
            return new EReaderException(MessageCode.BOOK_ID_NOT_FOUND);
        });

    }

    @Override
    public List<Book> getAllBook(BookFilter bookFilter, Pagination pagination) {
        Pageable pageable = PageRequest.of(pagination.getPage() - 1, pagination.getPageSize());
        Page<Book> pageBooks = bookRepository.findAll(pageable);
        pagination.setTotal(pageBooks.getTotalPages());
        return pageBooks.toList();
    }

    @Override
    public BookDetail getBookDetail(long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EReaderException("adsf"));
        List<Comment> comments = commentRepository.findAllByBookId(bookId);
        return BookDetail.builder()
                .comments(comments)
                .coverImageUrl(book.getCoverImageUrl())
                .id(bookId)
                .description(book.getDescription())
                .fileUrl(book.getFileUrl())
                .publishedYear(book.getPublishedYear())
                .rating(book.getRating())
                .title(book.getTitle())
                .totalPage(book.getTotalPage())
                .build();
    }
}
