package utc2.itk62.e_reader.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utc2.itk62.e_reader.domain.enums.ReadingStatus;

import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "reading_progresses")
public class ReadingProgress extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private int progress;
    private Instant completedAt;

    @Enumerated(EnumType.STRING)
    private ReadingStatus readingStatus;
}
