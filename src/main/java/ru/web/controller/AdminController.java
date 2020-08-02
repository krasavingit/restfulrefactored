package ru.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.web.models.Role;
import ru.web.models.User;
import ru.web.services.UserService;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/panel")
    public String getUsers(Model model) {
        model.addAttribute("userlist", userService.getAllUsers());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        model.addAttribute("authUsername", user.getUsername());
        model.addAttribute("authLastname", user.getLastname());
        model.addAttribute("authRole", user.getRoles());
        model.addAttribute("authID", user.getId());
        model.addAttribute("authEmail", user.getEmail());
        model.addAttribute("authAge", user.getAge());
        return "admin/panel";
    }

    @PostMapping("/panel/addUser")
    public String addUser(@RequestParam(name = "username") String username,@RequestParam(name = "lastname") String lastname,@RequestParam(name = "age") byte age, @RequestParam(name = "email") String email,
                          @RequestParam(name = "password") String password, @RequestParam(value = "role", required = true) Role role, Model model) {
        User user = new User();
//        if(userService.loadUserByUsername(username)!=null){
//            model.addAttribute("userExists", "User exists");
//        }else {
            user.setFirstName(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            System.out.println(user.getPassword());
            user.setLastname(lastname);
            user.setAge(age);
            model.addAttribute("roleadmin", Role.ROLE_ADMIN.getAuthority());
            model.addAttribute("roleuser", Role.ROLE_USER);
            Set<Role> set = new HashSet<Role>();
            if (role != null) {
                if (role.getAuthority().equals(Role.ROLE_ADMIN.getAuthority())) {
                    set.add(Role.ROLE_ADMIN);
                    set.add(Role.ROLE_USER);
                }
            }
            if (role != null) {
                if (role.getAuthority().equals(Role.ROLE_USER.getAuthority())) {
                    set.add(Role.ROLE_USER);
                }
            }
            user.setRoles(set);
            userService.addUser(user);
        return "redirect:/admin/panel";
    }

    @PostMapping("/panel/deleteUser")
    public String deleteUser(@RequestParam("id") Long id, Model model) {

        model.addAttribute("userID", userService.findOne(id).getId());
        model.addAttribute("firstname", userService.findOne(id).getUsername());
        model.addAttribute("lastname", userService.findOne(id).getLastname());
        model.addAttribute("age", userService.findOne(id).getAge());
        model.addAttribute("email", userService.findOne(id).getEmail());
        model.addAttribute("roles", userService.findOne(id).getAuthorities());
        userService.deleteById(id);
        return "redirect:/admin/panel";
    }

    @PostMapping(value = "/panel/editUser")
    public String editForm(@ModelAttribute("user") User user, @RequestParam("role") Role role) {
        Set<Role> set = new HashSet<Role>();
        if (user.getRoles() != null) {
            if (role.getAuthority().equals(Role.ROLE_ADMIN.getAuthority())) {
                set.add(Role.ROLE_ADMIN);
                set.add(Role.ROLE_USER);
            }
        }
        if (role != null) {
            if (role.getAuthority().equals(Role.ROLE_USER.getAuthority())) {
                set.add(Role.ROLE_USER);
            }
        }
        user.setRoles(set);
        userService.edit(user);

        return "redirect:/admin/panel";
    }
}