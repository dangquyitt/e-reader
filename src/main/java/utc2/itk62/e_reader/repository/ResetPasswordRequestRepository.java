package utc2.itk62.e_reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import utc2.itk62.e_reader.domain.entity.ResetPasswordRequest;

import java.util.Optional;

@Repository
public interface ResetPasswordRequestRepository extends JpaRepository<ResetPasswordRequest, Long> {
    Optional<ResetPasswordRequest> findByEmail(String email);

    @Modifying
    @Query("update ResetPasswordRequest rpr set rpr.status = :status where rpr.email = :email AND rpr.status != :status")
    void updateStatusByEmail(String email, String status);

    Optional<ResetPasswordRequest> findByToken(String token);
}
