package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.dto.CreateUserRequest;
import utc2.itk62.e_reader.model.User;

public interface IUserService {
    Long createUser(CreateUserRequest u);
}
