package com.example.Security_REST.dao;

import com.example.Security_REST.models.Role;

import java.util.List;

public interface RoleDAO {

    Role getRoleByName(String name);

    Role getDefaultRole();

    Role getAdminRole();

    List<Role> listAllRoles();
}
