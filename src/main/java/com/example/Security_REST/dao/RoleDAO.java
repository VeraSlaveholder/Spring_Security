package com.example.Security_REST.dao;

import com.example.Security_REST.models.Role;

import java.util.List;

public interface RoleDAO {

    List<Role> getAllRoles();

    Role getRole(String userRole);

    Role getRoleById(int id);

    void addRole(Role role);
}
