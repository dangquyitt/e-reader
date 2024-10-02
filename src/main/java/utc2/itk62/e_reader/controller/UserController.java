package utc2.itk62.e_reader.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import utc2.itk62.e_reader.core.error.Error;
import utc2.itk62.e_reader.exception.CustomException;
import utc2.itk62.e_reader.service.IUserService;

import java.util.ArrayList;

@RestController
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/internalEx")
    public ResponseEntity<Object> testInternalEx() {
        throw new RuntimeException("testInternalLog");
    }

    @GetMapping("/customEx")
    public ResponseEntity<Object> testCustomEx() {
        throw new CustomException().setStatus(400).addError(new Error("email", "User email is exists"));
    }
}
