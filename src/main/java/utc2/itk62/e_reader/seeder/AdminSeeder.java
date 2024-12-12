package utc2.itk62.e_reader.seeder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import utc2.itk62.e_reader.domain.entity.Role;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.domain.entity.UserRole;
import utc2.itk62.e_reader.repository.RoleRepository;
import utc2.itk62.e_reader.repository.UserRepository;
import utc2.itk62.e_reader.repository.UserRoleRepository;

import java.util.*;

@Component
@Slf4j
public class AdminSeeder implements CommandLineRunner {

    @Value("${user-admin.email}")
    private String adminEmail;

    @Value("${user-admin.password}")
    private String adminPassword;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) throws Exception {
        Optional<User> opUser = userRepository.findByEmail(adminEmail);
        User user;
        if (opUser.isPresent()) {
            user = opUser.get();
            user.setPassword(passwordEncoder.encode(adminPassword));
        } else {
            user = new User();
            user.setEmail(adminEmail);
            user.setPassword(passwordEncoder.encode(adminPassword));
            userRepository.save(user);
        }
        List<Role> roles = roleRepository.findAll();
        List<UserRole> userRoles = new ArrayList<>();
        for (Role role : roles) {
            boolean exists = userRoleRepository.existsByUserAndRole(user, role);
            if (exists) {
                continue;
            }
            UserRole userRole = new UserRole();
            userRole.setRole(role);
            userRole.setUser(user);
            userRoles.add(userRole);
        }
        userRoleRepository.saveAll(userRoles);
    }
}
