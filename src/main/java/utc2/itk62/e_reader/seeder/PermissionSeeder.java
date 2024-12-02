package utc2.itk62.e_reader.seeder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import utc2.itk62.e_reader.domain.entity.Permission;
import utc2.itk62.e_reader.domain.entity.Role;
import utc2.itk62.e_reader.domain.enums.MethodPermission;
import utc2.itk62.e_reader.repository.PermissionRepository;
import utc2.itk62.e_reader.repository.RoleRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Component
@Slf4j
public class PermissionSeeder implements CommandLineRunner {
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    private final String ID_REGEX = "\\d+";

    public PermissionSeeder(PermissionRepository permissionRepository, RoleRepository roleRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        List<Role> roles = roleRepository.findAll();

        for (Role role : roles) {
            if (role.getRoleName().equals("USER")) {
                List<Permission> permissions = new ArrayList<>((Collection) new Permission(
                        MethodPermission.GET,
                        "/book/" + "{" + ID_REGEX + "}",
                        new HashSet<>()));
                if (permissionRepository.count() != permissions.size()) {
                    for (Permission permission : permissions) {
                        permission.getRoles().add(role);
                        role.getPermissions().add(permission);

                        permissionRepository.save(permission);
                        log.info("Create permission with path: " + permission.getPath());
                    }

                }
            }
        }
        log.info("Permission seeding completed");
    }
}
