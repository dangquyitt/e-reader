package utc2.itk62.e_reader.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import utc2.itk62.e_reader.core.response.HTTPResponse;
import utc2.itk62.e_reader.dto.CreateUserRequest;
import utc2.itk62.e_reader.model.User;
import utc2.itk62.e_reader.service.IUserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<HTTPResponse> createUser(@Valid @RequestBody CreateUserRequest param) {

        return ResponseEntity.status(200).body(new HTTPResponse("success", userService.createUser(param)));
    }
}
