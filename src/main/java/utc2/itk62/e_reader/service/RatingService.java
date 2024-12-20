package utc2.itk62.e_reader.service;


import utc2.itk62.e_reader.domain.entity.Rating;


public interface RatingService {

    Rating addRating(Long userId, Long bookId, Double rating);

    Rating getRating(Long userId, Long bookId);

    Rating updateRating(Long id, Long userId, Double rating);

    void deleteRating(Long id, Long userId);
}
