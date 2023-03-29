package com.example.Security_REST.services;

import com.example.Security_REST.models.Role;

import java.util.List;

public interface RoleService {
    public Role getRoleByName(String name) ;

    public Role getDefaultRole();

    public Role getAdminRole();

    public List<Role> getAllRoles() ;
}
