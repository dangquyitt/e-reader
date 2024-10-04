package utc2.itk62.e_reader.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.dto.AuthenticationRequest;
import utc2.itk62.e_reader.service.IAuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthenticationService iAuthenticationService;

    @PostMapping("/login")
    public ResponseEntity<HTTPResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result =  iAuthenticationService.authenticate(request);
        return ResponseEntity.status(200)
                .body(new HTTPResponse("success",result));

    }
}
