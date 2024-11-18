package utc2.itk62.e_reader.domain.entity.key;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionId implements Serializable {

    private long userId;
    private long planId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionId that = (SubscriptionId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(planId, that.planId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, planId);
    }
}
