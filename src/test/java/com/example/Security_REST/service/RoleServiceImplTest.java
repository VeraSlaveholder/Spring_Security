package com.example.Security_REST.service;

import com.example.Security_REST.DAO.RoleDAOImpl;
import com.example.Security_REST.DAO.UserDAOImpl;
import com.example.Security_REST.model.Role;
import com.example.Security_REST.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RoleServiceImplTest {
    @Mock
    private RoleDAOImpl roleDAO;

    private RoleService roleService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        roleService = new RoleServiceImpl(roleDAO);
    }

    @Test
    void findAllRole() {
        List<Role> usersFromMock = new ArrayList<>();
        when(roleDAO.findAllRoles()).thenReturn(usersFromMock);
        List<Role> users = roleService.findAllRole();
        assertEquals(users, usersFromMock);
    }
    @Test
    void findByIdRoles() {
        Role role1=new Role(1, "ROLE_ADMIN");
        Role role2=new Role(2, "ROLE_USER");

        Set<Role> roles = new HashSet<>();

        roles.add(role1);
        roles.add(role2);
        when(roleDAO.findAllById(Arrays.asList(1, 2))).thenReturn(roles);

        RoleServiceImpl roleService = new RoleServiceImpl(roleDAO);
        Set<Role> result = roleService.findByIdRoles(Arrays.asList(1, 2));

        assertEquals(2, result.size());
        assertTrue(result.contains(role1));
        assertTrue(result.contains(role2));
    }
}