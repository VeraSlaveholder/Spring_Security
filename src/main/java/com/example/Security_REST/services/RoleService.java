package com.example.Security_REST.services;

import com.example.Security_REST.models.Role;

import java.util.List;

public interface RoleService {
    Role getRoleByName(String name);

    Role getDefaultRole();

    Role getAdminRole();

    List<Role> getAllRoles();
}
