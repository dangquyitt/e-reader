package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.core.pagination.Pagination;
import utc2.itk62.e_reader.domain.entity.Favorite;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.domain.model.FavoriteFilter;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.FavoriteRepository;
import utc2.itk62.e_reader.service.FavoriteService;

import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;


    @Override
    public Favorite addFavorite(Long userId, Long bookId) {
        Optional<Favorite> optionalFavorite = favoriteRepository.findByUserIdAndBookId(userId, bookId);

        if (optionalFavorite.isPresent()) {
            favoriteRepository.delete(optionalFavorite.get());
            return null;
        } else {
            Favorite newFavorite = new Favorite();
            newFavorite.setUserId(userId);
            newFavorite.setBookId(bookId);
            return favoriteRepository.save(newFavorite);
        }
    }

    @Override
    public List<Favorite> getAllFavorite(FavoriteFilter filter, Pagination pagination) {
        Specification<Favorite> spec = Specification.where(null);

        if (filter != null) {
            if (filter.getUserId() != null) {
                spec = spec.and(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("userId"), filter.getUserId())));
            }
        }

        Pageable pageable = PageRequest.of(pagination.getPage() - 1, pagination.getPageSize());
        Page<Favorite> pageFavorites = favoriteRepository.findAll(spec, pageable);
        pagination.setTotal(pageFavorites.getTotalPages());
        return pageFavorites.toList();
    }

    @Override
    public void deleteByUserIdAndBookId(Long userId, Long bookId) {
        Optional<Favorite> favorite = favoriteRepository.findByUserIdAndBookId(userId, bookId);
        favorite.ifPresent(favoriteRepository::delete);
    }

    @Override
    public void deleteFavoriteById(Long userId, Long favoriteId) {
        Favorite favorite = favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new EReaderException("not found favorite"));

        if (!favorite.getUserId().equals(userId)) {
            log.error("favorite {} not belong to user {}", favoriteId, userId);
            throw new EReaderException("favorite not belong to user");
        }
        
        favoriteRepository.delete(favorite);
    }
}
