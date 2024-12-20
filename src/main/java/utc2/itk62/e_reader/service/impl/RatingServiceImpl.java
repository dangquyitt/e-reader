package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Book;
import utc2.itk62.e_reader.domain.entity.Collection;
import utc2.itk62.e_reader.domain.entity.Rating;
import utc2.itk62.e_reader.domain.model.CollectionFilter;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.BookRepository;
import utc2.itk62.e_reader.repository.CollectionRepository;
import utc2.itk62.e_reader.repository.RatingRepository;
import utc2.itk62.e_reader.service.CollectionService;
import utc2.itk62.e_reader.service.RatingService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final BookRepository bookRepository;


    @Override
    public Rating addRating(Long userId, Long bookId, Double rating) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EReaderException("book id not found"));
        Optional<Rating> optionalRating = ratingRepository.findByUserIdAndBookId(userId, bookId);

        if (optionalRating.isPresent()) {
            throw new EReaderException("The rating already exists");
        } else {
            Rating newRating = new Rating();
            newRating.setUserId(userId);
            newRating.setBookId(bookId);
            newRating.setRating(rating);
            ratingRepository.save(newRating);
            List<Rating> ratings = ratingRepository.findAllByBookId(bookId);
            double averageRating = ratings.stream().mapToDouble(Rating::getRating).average().orElse(0.0);
            book.setRating(averageRating);
            bookRepository.save(book);
            return newRating;
        }
    }

    @Override
    public Rating getRating(Long userId, Long bookId) {
        return ratingRepository.findByUserIdAndBookId(userId, bookId).orElseThrow(() ->
                new EReaderException("not found rating by bookId and userId"));
    }

    @Override
    public Rating updateRating(Long id, Long userId, Double rating) {
        Book book = bookRepository.findBookByRatingId(id).orElseThrow(() -> new EReaderException("Book not found"));
        Rating optionalRating = ratingRepository.findByIdAndUserId(id, userId).orElseThrow(() ->
                new EReaderException("not found rating by id and userId"));
        optionalRating.setRating(rating);
        ratingRepository.save(optionalRating);
        List<Rating> ratings = ratingRepository.findAllByBookId(book.getId());
        double averageRating = ratings.stream().mapToDouble(Rating::getRating).average().orElse(0.0);
        book.setRating(averageRating);
        bookRepository.save(book);
        return optionalRating;
    }

    @Override
    public void deleteRating(Long id, Long userId) {
        Book book = bookRepository.findBookByRatingId(id).orElseThrow(() -> new EReaderException("Book not found"));
        Optional<Rating> rating = ratingRepository.findByIdAndUserId(id, userId);
        rating.ifPresent(ratingRepository::delete);
        List<Rating> ratings = ratingRepository.findAllByBookId(book.getId());
        double averageRating = ratings.stream().mapToDouble(Rating::getRating).average().orElse(0.0);
        book.setRating(averageRating);
        bookRepository.save(book);
    }
}
