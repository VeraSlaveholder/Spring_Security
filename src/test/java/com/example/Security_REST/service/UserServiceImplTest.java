package com.example.Security_REST.service;

import com.example.Security_REST.DAO.UserRepository;
import com.example.Security_REST.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UserServiceImplTest {
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    public void findAll() {
        List<User> usersFromMock = new ArrayList<>();
        Mockito.when(userRepository.findAll()).thenReturn(usersFromMock);
        List<User> users = userService.findAll();
        assertEquals(users, usersFromMock);
    }

    @Test
    public void getById() {
        int entityId = 123;
        User expectedUser = new User();
        expectedUser.setUserId(entityId);
        when(userRepository.getUserByUserId(entityId)).thenReturn(expectedUser);

        User actualUser = userService.getById(entityId);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void save() {
        User user = new User();
        user.setAge(12);
        user.setName("user");
        user.setPassword("password");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        userService.save(user);

        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(argThat(savedUser -> savedUser.getName().equals("user") &&
                (savedUser.getAge() == 12) &&
                savedUser.getPassword().equals("encodedPassword")));
        verifyNoMoreInteractions(passwordEncoder, userRepository);
    }


    @Test
    public void deleteById() {
        int id = 1;
        userService.deleteById(id);

        verify(userRepository, times(1)).deleteByUserId(id);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testFindByUsername() {
        String username = "name";
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(username);

        User expectedUser = new User();
        expectedUser.setName(username);
        when(userRepository.findByName(username)).thenReturn(expectedUser);

        User actualUser = userService.findByUsername(principal);

        verify(userRepository, times(1)).findByName(username);
        verifyNoMoreInteractions(userRepository);

        assertNotNull(actualUser);
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
    }

    @Test
    public void findByUsernameWithNullPrincipal() {
        User actualUser = userService.findByUsername(null);
        assertNull(actualUser);
        verifyZeroInteractions(userRepository);
    }

    @Test
    public void updateWithMatchingPasswords() {
        int id = 1;
        String username = "johndoe";
        String oldPassword = "password";
        String newPassword = "newpassword";

        User existingUser = new User();
        existingUser.setUserId(id);
        existingUser.setName(username);
        existingUser.setPassword(oldPassword);

        User updatedUser = new User();
        updatedUser.setUserId(id);
        updatedUser.setName(username);
        updatedUser.setPassword(oldPassword);

        when(userRepository.getUserByUserId(id)).thenReturn(existingUser);
        when(passwordEncoder.encode(newPassword)).thenReturn(newPassword);

        userService.update(id, updatedUser);

        verify(userRepository, times(1)).getUserByUserId(id);
        verify(userRepository, times(1)).save(updatedUser);
        verifyNoMoreInteractions(userRepository);

        assertEquals(existingUser.getUsername(), updatedUser.getUsername());
        assertEquals(existingUser.getPassword(), updatedUser.getPassword());
    }

    @Test
    public void updateWithDifferentPasswords() {
        int id = 1;
        String username = "johndoe";
        String oldPassword = "password";
        String newPassword = "newpassword";

        User existingUser = new User();
        existingUser.setUserId(id);
        existingUser.setName(username);
        existingUser.setPassword(oldPassword);

        User updatedUser = new User();
        updatedUser.setUserId(id);
        updatedUser.setName(username);
        updatedUser.setPassword(newPassword);

        when(userRepository.getUserByUserId(id)).thenReturn(existingUser);
        when(passwordEncoder.encode(newPassword)).thenReturn(newPassword);

        userService.update(id, updatedUser);

        verify(userRepository, times(1)).getUserByUserId(id);
        verify(userRepository, times(1)).save(updatedUser);
        verifyNoMoreInteractions(userRepository);

        assertEquals(existingUser.getUsername(), updatedUser.getUsername());
        assertNotEquals(existingUser.getPassword(), updatedUser.getPassword());
        assertEquals(newPassword, updatedUser.getPassword());
    }

    @Test
    public void passwordCoder() {
        User user = new User();
        user.setUserId(1);
        user.setPassword("password");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        User result = userService.passwordCoder(user);

        verify(passwordEncoder, times(1)).encode("password");
        verifyNoMoreInteractions(passwordEncoder);

        assertEquals("encodedPassword", result.getPassword());
    }
}
