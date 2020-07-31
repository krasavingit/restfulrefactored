package ru.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.web.models.User;
import ru.web.services.UserService;

@RestController(value = "api/v1")
public class UserRestComtroller {

    private final UserService userService;

    @Autowired
    public UserRestComtroller(UserService userService) {
        this.userService = userService;
    }
    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getUser(@RequestParam("id") Long id){
        User user = userService.findOne(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
