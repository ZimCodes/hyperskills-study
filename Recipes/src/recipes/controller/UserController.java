package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import recipes.model.User;
import recipes.service.UserService;

import javax.validation.Valid;

@RestController
public class UserController {
    UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/api/register")
    public ResponseEntity<HttpStatus> registerUser(@Valid @RequestBody User user) {
        boolean exists = this.service.existsUserByEmail(user.getEmail());
        if (exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        this.service.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}