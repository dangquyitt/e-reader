package utc2.itk62.e_reader.seeder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import utc2.itk62.e_reader.domain.entity.Role;
import utc2.itk62.e_reader.domain.enums.RoleName;
import utc2.itk62.e_reader.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;

@Component
@Slf4j
public class RoleSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Role> rolesToSees = List.of(
                new Role(RoleName.ADMIN, new HashSet<>()),
                new Role(RoleName.USER, new HashSet<>())
        );

        for (Role role : rolesToSees) {
            if (!roleRepository.existsByRoleName(role.getRoleName())) {
                roleRepository.save(role);
                log.info("Create role: " + role.getRoleName());
            }

        }

        log.info("Role seeding completed");
    }
}
