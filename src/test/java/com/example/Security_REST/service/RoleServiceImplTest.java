package com.example.Security_REST.service;

import com.example.Security_REST.DAO.RoleRepository;
import com.example.Security_REST.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;

    private RoleService roleService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    void findAllRole() {
        List<Role> usersFromMock = new ArrayList<>();
        when(roleRepository.findAll()).thenReturn(usersFromMock);
        List<Role> users = roleService.findAllRole();
        assertEquals(users, usersFromMock);
    }

    @Test
    void findByIdRoles() {
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");

        Set<Role> roles = new HashSet<>();

        roles.add(role1);
        roles.add(role2);
        when(roleRepository.findAllById(Arrays.asList(1, 2))).thenReturn(roles);

        RoleServiceImpl roleService = new RoleServiceImpl(roleRepository);
        Set<Role> result = roleService.findByIdRoles(Arrays.asList(1, 2));

        assertEquals(2, result.size());
        assertTrue(result.contains(role1));
        assertTrue(result.contains(role2));
    }
}