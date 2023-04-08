package com.example.Security_REST.services;

import com.example.Security_REST.models.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    Role getRole(String userRole);

    Role getRoleById(int id);

    void addRole(Role role);
}
