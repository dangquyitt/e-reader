package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.domain.entity.*;
import utc2.itk62.e_reader.domain.model.CreateBookParam;
import utc2.itk62.e_reader.domain.model.UpdateBookParam;

import utc2.itk62.e_reader.exception.CustomException;
import utc2.itk62.e_reader.repository.*;
import utc2.itk62.e_reader.service.BookService;
import utc2.itk62.e_reader.service.FileService;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final FileService fileService;
    private final SubscriptionRepository subscriptionRepository;
    private final PlanVersionRepository planVersionRepository;
    private final UserBookAccessRepository userBookAccessRepository;
    private final UserRepository userRepository;
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
    public Book getBook(Long bookId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new CustomException().addError(new Error("id","user.id.not_found")));
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new CustomException().addError(new Error("id","book.id.not_found")));
        if(enrollBook(userId)){
            if(!userBookAccessRepository.existsByUserId(userId)){
                UserBookAccess userBookAccess = UserBookAccess.builder()
                        .user(user)
                        .book(book)
                        .accessDate(Instant.now())
                        .build();
                userBookAccessRepository.save(userBookAccess);
            }
            UserBookAccess userBookAccess = userBookAccessRepository.findByUserId(userId);
            userBookAccess.setAccessDate(Instant.now());
            userBookAccessRepository.save(userBookAccess);
            return book;
        }
        throw new CustomException().addError(new Error("id", "user.id.not_enroll"));
    }

    @Override
    public List<Book> getAllBook() {
        return bookRepository.findAll();
    }

    private boolean enrollBook(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new CustomException().addError(new Error("id","user.id.not_found")));
        List<Subscription> subscriptions = subscriptionRepository.findActiveSubscriptionsByUserId(userId, Instant.now());
        if(subscriptions.isEmpty()){
            throw new CustomException().addError(new Error("id","user.id.has_no_subscription"));
        }
        for (Subscription subscription:subscriptions) {
            Plan plan = subscription.getPlan();
            PlanVersion planVersion = planVersionRepository
                    .findLatestVersionByPlanIdAndSubscriptionDate(plan.getId(), LocalDate.from(subscription.getStartTime()))
                    .orElseThrow(() ->
                            new CustomException().addError(new Error("id","plan.id.has_no_version_by_id")));
            List<UserBookAccess> accesses = userBookAccessRepository
                    .findAllByUserAndAccessDateBetween(user,subscription.getStartTime(),subscription.getEndTime());
            if(accesses.size()< planVersion.getMaxEnrollBook()){
                return true;
            }
        }

        return false;
    }
}
