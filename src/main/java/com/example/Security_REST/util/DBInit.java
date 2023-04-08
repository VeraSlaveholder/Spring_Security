package com.example.Security_REST.util;


import com.example.Security_REST.models.Role;
import com.example.Security_REST.models.Users;
import com.example.Security_REST.services.RoleService;
import com.example.Security_REST.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DBInit {
    private final UsersService userService;
    private final RoleService roleService;

    @Autowired
    public DBInit(UsersService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void dataBaseInit() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        Set<Role> adminSet = new HashSet<>();
        Set<Role> userSet = new HashSet<>();

        roleService.addRole(roleAdmin);
        roleService.addRole(roleUser);

        adminSet.add(roleAdmin);
        adminSet.add(roleUser);
        userSet.add(roleUser);

        Users newUser = new Users("user", 23, "Ivanov", "ivan@mail.com",
                "user", userSet);
        Users admin = new Users("aa", 30, "Potter", "garry@gmail.com",
                "aa", adminSet);

        userService.save(newUser);
        userService.save(admin);
    }
}
