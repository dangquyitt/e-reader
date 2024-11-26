package utc2.itk62.e_reader.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.domain.entity.Role;
import utc2.itk62.e_reader.dto.CreateRoleRequest;
import utc2.itk62.e_reader.dto.RoleResponse;
import utc2.itk62.e_reader.dto.UpdateRoleRequest;
import utc2.itk62.e_reader.service.RoleService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/roles")

@AllArgsConstructor
public class RoleController {

    private final MessageSource messageSource;
    private final RoleService roleService;


    @PostMapping
    public ResponseEntity<HTTPResponse> create(@RequestBody CreateRoleRequest request, Locale locale) {
        Role role = roleService.createRole(request.getRoleName());
        RoleResponse roleResponse = RoleResponse.builder()
                .id(role.getId())
                .roleName(role.getRoleName().toString())
                .build();

        String message = messageSource.getMessage("role.created.success", null, locale);
        return HTTPResponse.success(message,roleResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HTTPResponse> update(@RequestBody UpdateRoleRequest request, Locale locale) {
        Role role = roleService.updateRole(request.getRoleName(), request.getId());
        RoleResponse roleResponse = RoleResponse.builder()
                .id(role.getId())
                .roleName(role.getRoleName().toString())
                .build();

        String message = messageSource.getMessage("role.update.success", null, locale);
        return HTTPResponse.success(message, roleResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HTTPResponse> getRole(@PathVariable long id) {
        Role role = roleService.getRole(id);
        RoleResponse roleResponse = RoleResponse.builder()
                .id(role.getId())
                .roleName(role.getRoleName().toString())
                .build();
        return HTTPResponse.success(roleResponse);
    }
    @GetMapping
    public ResponseEntity<HTTPResponse> getAllRole() {

        List<RoleResponse> roleResponses = roleService.getAllRole()
                .stream().map(role -> RoleResponse.builder()
                        .id(role.getId())
                        .roleName(role.getRoleName().toString())
                        .build()).collect(Collectors.toList());

        return HTTPResponse.success(roleResponses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HTTPResponse> deleteRole(@PathVariable long id) {

        String message = "";
        if(roleService.deleteRole(id)){
            message = "Delete role successfully";
        }

        return HTTPResponse.success(message);
    }
}
