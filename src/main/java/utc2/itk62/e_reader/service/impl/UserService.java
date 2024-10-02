package utc2.itk62.e_reader.service.impl;

import org.springframework.stereotype.Service;
import utc2.itk62.e_reader.model.User;
import utc2.itk62.e_reader.repository.IUserRepository;
import utc2.itk62.e_reader.service.IUserService;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
