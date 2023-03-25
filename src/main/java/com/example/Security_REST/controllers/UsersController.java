package com.example.Security_REST.controllers;

import com.example.Security_REST.models.Users;
import com.example.Security_REST.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UsersController {
    private final UsersService userService;

    @Autowired
    public UsersController(UsersService userService) {
        this.userService = userService;
    }


    @GetMapping("/user")
    public String showUserByIdForUser(Principal principal, Model model) {
        String username = principal.getName();
        Users user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "user";
    }
}
