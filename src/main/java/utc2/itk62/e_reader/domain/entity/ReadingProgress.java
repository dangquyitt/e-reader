package utc2.itk62.e_reader.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "reading_progresses", schema = "public", indexes = {
        @Index(name = "reading_progresses_user_id_book_id_idx", columnList = "user_id, book_id", unique = true)
})
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "id", nullable = false)),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false))
})
public class ReadingProgress extends BaseEntity {
    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "last_read_position", nullable = false)
    private Integer lastReadPosition;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "progress", nullable = false)
    private Double progress;

    @NotNull
    @ColumnDefault("'IN_PROGRESS'")
    @Column(name = "reading_status", nullable = false, length = Integer.MAX_VALUE)
    private String readingStatus;

    @Column(name = "completed_at")
    private Instant completedAt;

}