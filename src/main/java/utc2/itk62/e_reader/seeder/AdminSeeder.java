package utc2.itk62.e_reader.seeder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import utc2.itk62.e_reader.domain.entity.Role;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.domain.entity.UserRole;
import utc2.itk62.e_reader.repository.RoleRepository;
import utc2.itk62.e_reader.repository.UserRepository;
import utc2.itk62.e_reader.repository.UserRoleRepository;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@Order(2)
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
        User user = userRepository.findByEmail(adminEmail).orElse(
                User.builder().email(adminEmail).password(passwordEncoder.encode(adminPassword)).build()
        );
        user.setEmailVerifiedAt(Instant.now());
        userRepository.save(user);

        List<Role> roles = roleRepository.findAll();

        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());
        Set<Long> existingRoleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .collect(Collectors.toSet());

        List<UserRole> newUserRoles = roles.stream()
                .filter(role -> !existingRoleIds.contains(role.getId())) // Lọc các role chưa có
                .map(role -> UserRole.builder().userId(user.getId()).roleId(role.getId()).build()) // Tạo UserRole mới
                .toList();

        if (!newUserRoles.isEmpty()) {
            userRoleRepository.saveAll(newUserRoles);
        }
    }
}
