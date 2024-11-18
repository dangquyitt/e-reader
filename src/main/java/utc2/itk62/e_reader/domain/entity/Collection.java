package utc2.itk62.e_reader.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import utc2.itk62.e_reader.domain.entity.key.CommentId;

import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "collections")
public class Collection extends BaseEntity{

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
