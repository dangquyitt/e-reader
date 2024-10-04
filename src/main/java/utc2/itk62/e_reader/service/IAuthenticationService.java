package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.dto.AuthenticationRequest;

public interface IAuthenticationService {
    boolean authenticate(AuthenticationRequest request);
}
