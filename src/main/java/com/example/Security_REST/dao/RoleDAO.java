package com.example.Security_REST.dao;

import com.example.Security_REST.models.Role;

import java.util.List;

public interface RoleDAO {

    public Role getRoleByName(String name) ;

    public Role getDefaultRole();

    public Role getAdminRole();

    public List<Role> listAllRoles() ;
}
