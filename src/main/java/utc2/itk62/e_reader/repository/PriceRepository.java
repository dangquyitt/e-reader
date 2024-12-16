package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utc2.itk62.e_reader.domain.entity.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

}
