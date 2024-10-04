package utc2.itk62.e_reader.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String password;
    private String firstName;
    private String lastName;
    private String email;

    @ManyToMany
    private Set<Role> roles;
}
