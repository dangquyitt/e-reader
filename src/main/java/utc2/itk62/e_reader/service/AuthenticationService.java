package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.domain.entity.User;

public interface AuthenticationService {
    User login(String email, String password);

    User register(String email, String password);
}
