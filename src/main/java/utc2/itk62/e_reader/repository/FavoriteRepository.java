package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import utc2.itk62.e_reader.domain.entity.Favorite;
import utc2.itk62.e_reader.domain.entity.key.FavoriteId;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {
}
