package com.example.Security_REST.controllers;

import com.example.Security_REST.models.Role;
import com.example.Security_REST.models.Users;
import com.example.Security_REST.services.RoleService;
import com.example.Security_REST.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsersService usersService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UsersService usersService, RoleService roleService) {
        this.usersService = usersService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String index(Principal principal,Model model) {
        String username = principal.getName();
        Users user = usersService.findByUsername(username);
        model.addAttribute("admin", user);
        model.addAttribute("users", usersService.findAll());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/all";
    }

    @PostMapping()
    public String create(@ModelAttribute("newUser") @Valid Users users,BindingResult bindingResult,
                         @RequestParam("selectedRole") String[] selectedRole) {
        if (bindingResult.hasErrors())
            return "redirect:/admin";
        for (String role : selectedRole
        ) {
            if (role.contains("ROLE_USER")) {
                users.getRoles().add(roleService.getDefaultRole());
            } else if (role.contains("ROLE_ADMIN")) {
                users.getRoles().add(roleService.getAdminRole());
            }
        }
        usersService.save(users);
        return "redirect:/admin";
    }
    @PatchMapping("/update")
    public String updateUser(@ModelAttribute("user") @Valid Users user, BindingResult bindingResult,
                             @RequestParam("selectedRole") String[] selectedRole) {
//        if (bindingResult.hasErrors())
//            return "redirect:/admin";

        for (String role : selectedRole) {
            if (role.contains("ROLE_USER")) {
                user.getRoles().add(roleService.getDefaultRole());
            } else if (role.contains("ROLE_ADMIN")) {
                user.getRoles().add(roleService.getAdminRole());
            }
        }

        usersService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        usersService.delete(id);
        return "redirect:/admin";
    }
    @GetMapping("/user")
    public String showUserById(Principal principal, Model model) {
        String username = principal.getName();
        Users user = usersService.findByUsername(username);
        model.addAttribute("user", user);
        return "admin/user";
    }
}