package utc2.itk62.e_reader.seeder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.domain.entity.Role;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.domain.entity.UserRole;
import utc2.itk62.e_reader.domain.enums.RoleName;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.RoleRepository;
import utc2.itk62.e_reader.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class AdminSeeder implements CommandLineRunner {

    @Value("${user-admin.email}")
    private String adminEmail;

    @Value("${user-admin.password}")
    private String adminPassword;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            Role role = roleRepository.findById(1L).orElseThrow(() -> {
                log.error("AdminSeeder | id: {} not found", 1);
                throw new EReaderException(MessageCode.ROLE_PERMISSION_EXISTS);
            });
            role.getUserRoles().size();
            User adminUser = User.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .build();
            userRepository.save(adminUser);
            UserRole userRole = new UserRole(role, adminUser);
            Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(userRole);
            adminUser.setUserRoles(userRoles);
            userRepository.save(adminUser);
            log.info("Create Admin account!");
        }
        log.info("Admin seeding complete");
    }
}
