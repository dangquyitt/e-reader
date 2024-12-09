package utc2.itk62.e_reader.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PermissionResponse {

    private Long id;
    private String methodPermission;
    private String path;

}
