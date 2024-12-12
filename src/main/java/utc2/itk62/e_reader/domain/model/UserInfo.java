package utc2.itk62.e_reader.domain.model;

import lombok.Data;
import utc2.itk62.e_reader.domain.entity.Role;

import java.util.List;

@Data
public class UserInfo {
    private Long id;
    private String email;
    List<Role> roles;
}
