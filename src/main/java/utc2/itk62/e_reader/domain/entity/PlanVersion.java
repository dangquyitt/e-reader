package utc2.itk62.e_reader.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "plan_versions")
public class PlanVersion extends BaseEntity{

    private Instant effectiveTime;
    private int maxEnrollBook;
    private double price;



    @ManyToOne
    private Plan plan;
}
