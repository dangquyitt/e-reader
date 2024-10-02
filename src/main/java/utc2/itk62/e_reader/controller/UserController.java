package utc2.itk62.e_reader.controller;

import org.springframework.web.bind.annotation.RestController;
import utc2.itk62.e_reader.service.IUserService;

@RestController
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }
}
