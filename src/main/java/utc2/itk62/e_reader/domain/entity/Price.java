package utc2.itk62.e_reader.domain.entity;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import io.hypersistence.utils.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "prices", schema = "public")
@AttributeOverrides({
        @AttributeOverride(name = "id", column = @Column(name = "id", nullable = false)),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "updated_at", nullable = false))
})
public class Price extends BaseEntity {
    @NotNull
    @ColumnDefault("0")
    @Column(name = "amount", nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @NotNull
    @ColumnDefault("'USD'")
    @Column(name = "currency", nullable = false, length = Integer.MAX_VALUE)
    private String currency;

    @NotNull
    @Column(name = "plan_id", nullable = false)
    private Long planId;

    @ColumnDefault("'{}'")
    @Column(name = "features")
    @Type(ListArrayType.class)
    private List<String> features;

    @NotNull
    @Column(name = "duration_unit", nullable = false, length = Integer.MAX_VALUE)
    private String durationUnit;

    @NotNull
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @NotNull
    @ColumnDefault("'{}'")
    @Column(name = "metadata", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> metadata;

}