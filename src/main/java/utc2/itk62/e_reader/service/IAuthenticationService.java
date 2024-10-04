package utc2.itk62.e_reader.service;

import utc2.itk62.e_reader.core.response.AuthenticationResponse;
import utc2.itk62.e_reader.dto.AuthenticationRequest;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
