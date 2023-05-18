package com.example.Security_REST.service;

import com.example.Security_REST.DAO.RoleRepository;
import com.example.Security_REST.model.Role;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    private final RoleRepository roleRepository = Mockito.mock(RoleRepository.class);

    private final RoleService testingClass = Mockito.spy(new RoleServiceImpl(roleRepository));

    @Test
    void findAllRole() {
        List<Role> usersFromMock = new ArrayList<>();
        when(roleRepository.findAll()).thenReturn(usersFromMock);
        List<Role> users = testingClass.findAllRole();
        assertEquals(users, usersFromMock);
        verify(testingClass, times(1)).findAllRole();
    }

    @Test
    void findByIdRoles() {
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");

        Set<Role> roles = new HashSet<>();
        roles.add(role1);
        roles.add(role2);

        List<Integer> roleIds = Arrays.asList(1, 2);
        when(roleRepository.findAllById(roleIds)).thenReturn(roles);

        Set<Role> result = testingClass.findByIdRoles(roleIds);

        assertEquals(2, result.size());
        assertTrue(result.contains(role1));
        assertTrue(result.contains(role2));
        verify(testingClass, times(1)).findByIdRoles(roleIds);
    }
}