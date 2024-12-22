package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.*;
import utc2.itk62.e_reader.domain.model.BookFilter;
import utc2.itk62.e_reader.domain.model.CreateBookParam;
import utc2.itk62.e_reader.domain.model.OrderBy;
import utc2.itk62.e_reader.domain.model.UpdateBookParam;
import utc2.itk62.e_reader.dto.book.BookDetail;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.*;
import utc2.itk62.e_reader.service.BookService;
import utc2.itk62.e_reader.service.FileService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final FavoriteRepository favoriteRepository;
    private final FileService fileService;
    private final CommentRepository commentRepository;
    private final CollectionRepository collectionRepository;
    private final TagRepository tagRepository;
    private final BookTagRepository bookTagRepository;

    @Override
    public Book createBook(CreateBookParam createBookParam) {

        Book book = Book.builder()
                .title(createBookParam.getTitle())
                .description(createBookParam.getDescription())
                .publishedYear(createBookParam.getPublishedYear())
                .totalPage(createBookParam.getTotalPage())
                .fileUrl(fileService.uploadFile(createBookParam.getFileBook()))
                .coverImageUrl(fileService.uploadFile(createBookParam.getFileCoverImage()))
                .build();
        bookRepository.save(book);

        if (createBookParam.getTagIds() != null && !createBookParam.getTagIds().isEmpty()) {
            List<BookTag> bookTags = createBookParam.getTagIds().stream().map(tagId -> {
                BookTag bookTag = new BookTag();
                bookTag.setBookId(book.getId());
                bookTag.setTagId(tagId);
                return bookTag;
            }).toList();
            bookTagRepository.saveAll(bookTags);
        }

        return book;
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
    @Transactional
    public Book updateBook(UpdateBookParam updateBookParam) {
        Book book = bookRepository.findById(updateBookParam.getId()).orElseThrow(() -> {
            log.error("BookServiceImpl | id: {} not found", updateBookParam.getId());
            return new EReaderException(MessageCode.BOOK_ID_NOT_FOUND);
        });
        if (updateBookParam.getFileBook() != null) {
            fileService.deleteFile(book.getFileUrl());
            book.setFileUrl(fileService.uploadFile(updateBookParam.getFileBook()));
        }
        if (updateBookParam.getFileCoverImage() != null) {
            fileService.deleteFile(book.getCoverImageUrl());
            book.setCoverImageUrl(fileService.uploadFile(updateBookParam.getFileCoverImage()));
        }
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
    public List<Book> getAllBook(BookFilter filter, OrderBy orderBy, Pagination pagination) {
        Specification<Book> spec = Specification.where(null);
        if (!CollectionUtils.isEmpty(filter.getIds())) {
            spec = spec.and(((root, query, cb) -> root.get("id").in(filter.getIds())));
        }
        if (filter.getTitle() != null) {
            String titlePattern = "%" + filter.getTitle() + "%";
            spec = spec.and(((root, query, cb) -> cb.like(root.get("title"), titlePattern)));
        }

        Sort sort = null;
//        if (orderBy != null) {
//            Sort.Direction direction = orderBy.getOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
//            sort = Sort.by(direction, orderBy.getField());
//        }

        Pageable pageable = PageRequest.of(pagination.getPage() - 1, pagination.getPageSize());
        Page<Book> pageBooks = bookRepository.findAll(spec, pageable);
        pagination.setTotal(pageBooks.getTotalElements());
        return pageBooks.toList();
    }

    @Override
    public BookDetail getBookDetail(long bookId, long userId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EReaderException("adsf"));
        List<Comment> comments = commentRepository.findAllByBookId(bookId);
        List<Collection> collections = collectionRepository.findAllByUserIdAndBookId(userId, bookId);
        List<Tag> tags = tagRepository.findAllByBookId(bookId);
        BookDetail bookDetail = BookDetail.builder()
                .comments(comments)
                .coverImageUrl(book.getCoverImageUrl())
                .id(bookId)
                .description(book.getDescription())
                .fileUrl(book.getFileUrl())
                .publishedYear(book.getPublishedYear())
                .rating(book.getRating())
                .title(book.getTitle())
                .totalPage(book.getTotalPage())
                .isFavorite(false)
                .collections(collections)
                .tags(tags)
                .build();
        Optional<Favorite> favorite = favoriteRepository.findByUserIdAndBookId(userId, bookId);
        if (favorite.isPresent()) {
            bookDetail.setFavorite(true);
        }
        return bookDetail;
    }

    @Override
    public List<Book> getBooksByCollectionIds(List<Long> collectionIds, Pagination pagination) {
        if (collectionIds.isEmpty()) {
            return Collections.emptyList();
        }

        int page = pagination.getPage();
        int pageSize = pagination.getPageSize();
        int offset = (page - 1) * pageSize;

        List<Book> books = bookRepository.findBooksByCollectionIds(
                collectionIds,
                pageSize,
                offset
        );
        long totalBooks = bookRepository.countByCollectionIds(collectionIds);
        pagination.setTotal((int) Math.ceil((double) totalBooks / pageSize));

        return books;
    }

    @Override
    public List<Book> getBooksByAuthorId(Long authorId) {
        List<Book> books = bookRepository.findBooksByAuthorId(authorId);
        return books;
    }
}
