package utc2.itk62.e_reader.service;

import com.nimbusds.jose.JOSEException;
import utc2.itk62.e_reader.core.response.AuthenticationResponse;
import utc2.itk62.e_reader.core.response.IntrospectResponse;
import utc2.itk62.e_reader.dto.AuthenticationRequest;
import utc2.itk62.e_reader.dto.IntrospectRequest;

import java.text.ParseException;

public interface IAuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
