package com.example.Security_REST.controllers;

import com.example.Security_REST.models.Users;
import com.example.Security_REST.services.RoleService;
import com.example.Security_REST.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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
    public String index(Model model) {
        model.addAttribute("users", usersService.findAll());
        return "admin/all";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", usersService.findOne(id));
        return "admin/show";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") Users user, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") Users users, @RequestParam("selectedRole") String[] selectedRole,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "admin/new";

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

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", usersService.findOne(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid Users user, BindingResult bindingResult,
                         @RequestParam("selectedRole") String[] selectedRole) {
        if (bindingResult.hasErrors())
            return "admin/edit";

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
}