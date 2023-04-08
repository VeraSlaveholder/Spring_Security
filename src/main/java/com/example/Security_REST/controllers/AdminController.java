package com.example.Security_REST.controllers;

import com.example.Security_REST.models.Role;
import com.example.Security_REST.models.Users;
import com.example.Security_REST.services.RoleService;
import com.example.Security_REST.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;


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
    public String index(@AuthenticationPrincipal Users user, Model model) {
        model.addAttribute("users", usersService.findAll());
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
//        model.addAttribute("use", user);
        return "admin/all";
    }

    @PostMapping()
    public String create(@ModelAttribute("use") Users user) {
        getUserRoles(user);
        usersService.save(user);return "redirect:/admin";
    }

    @PutMapping("/{id}/update")
    public String updateUser(@ModelAttribute("user") Users user, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        getUserRoles(user);
        usersService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
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

    private void getUserRoles(Users user) {
        user.setRoles(user.getRoles().stream()
                .map(role -> roleService.getRole(role.getRoleName()))
                .collect(Collectors.toSet()));
    }
}