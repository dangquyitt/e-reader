package utc2.itk62.e_reader.service.impl;

import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.model.User;
import utc2.itk62.e_reader.repository.UserRepository;

@Service
public class UserServiceImpl implements utc2.itk62.e_reader.service.UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long createUser(User u) {
        return userRepository.save(u).getId();
    }
}
