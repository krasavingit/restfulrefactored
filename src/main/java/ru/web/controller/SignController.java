package ru.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.web.models.Role;
import ru.web.models.User;
import ru.web.services.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/")
public class SignController {


    private final UserService userService;

    @Autowired
    public SignController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/register")
    public String rigester(){
        return "/register.html";
    }

    @PostMapping("/register/signUP")
    public String addUser(@RequestParam(name = "username") String username, @RequestParam(name = "email") String email,
                          @RequestParam(name = "password") String password){
        User user = new User();
        user.setFirstName(username);
        user.setEmail(email);
        user.setPassword(password);
        Set<Role> set = new HashSet<>();
        set.add(Role.ROLE_USER);
        user.setRoles(set);
        userService.addUser(user);
        return "redirect:/login";
    }
}
