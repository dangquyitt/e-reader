package utc2.itk62.e_reader.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class RoleResponse {

    private Long id;
    private String roleName;

}
