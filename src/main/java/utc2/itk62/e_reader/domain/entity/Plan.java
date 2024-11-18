package utc2.itk62.e_reader.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utc2.itk62.e_reader.domain.enums.Currency;
import utc2.itk62.e_reader.domain.enums.DurationUnit;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "plans")
public class Plan extends BaseEntity{

    private String name;
    private double price;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private int duration;

    @Enumerated(EnumType.STRING)
    private DurationUnit durationUnit;

    @OneToMany(mappedBy = "plan",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Subscription> subscriptions;

}
