package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.domain.entity.User;
import utc2.itk62.e_reader.domain.model.UserInfo;

public interface AuthenticationService {
    UserInfo login(String email, String password);

    User register(String email, String password);
}
