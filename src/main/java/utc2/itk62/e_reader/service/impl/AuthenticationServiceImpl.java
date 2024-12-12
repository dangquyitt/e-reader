package utc2.itk62.e_reader.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.component.Translator;
import utc2.itk62.e_reader.constant.MessageCode;
import utc2.itk62.e_reader.domain.entity.Role;
import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.domain.entity.UserRole;
import utc2.itk62.e_reader.domain.model.UserInfo;
import utc2.itk62.e_reader.exception.EReaderException;
import utc2.itk62.e_reader.repository.RoleRepository;
import utc2.itk62.e_reader.repository.UserRepository;
import utc2.itk62.e_reader.repository.UserRoleRepository;
import utc2.itk62.e_reader.service.AuthenticationService;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public UserInfo login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("AuthenticationServiceImpl | email: {} not exists", email);
                    return new EReaderException(MessageCode.USER_CREDENTIALS_INVALID);
                });

        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.error("AuthenticationServiceImpl | password invalid");
            throw new EReaderException(MessageCode.USER_CREDENTIALS_INVALID);
        }
        List<Role> roles = roleRepository.findAllByUserId(user.getId());
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(user.getEmail());
        userInfo.setId(user.getId());
        userInfo.setRoles(roles);
        return userInfo;
    }

    @Override
    public User register(String email, String password) {
        userRepository.findByEmail(email)
                .ifPresent(value -> {
                    log.error("AuthenticationServiceImpl | email: {} already exists", email);
                    throw new EReaderException(MessageCode.USER_EMAIL_EXISTS);
                });
        User user = User.builder().email(email).password(passwordEncoder.encode(password)).build();
        userRepository.save(user);
        return user;
    }
}
