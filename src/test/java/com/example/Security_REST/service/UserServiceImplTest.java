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

    private final PasswordEncoder passwordEncoder=Mockito.mock(PasswordEncoder.class);

    private final UserRepository userRepository=Mockito.mock(UserRepository.class);

    private final UserService testClass=Mockito.spy(new UserServiceImpl(userRepository,passwordEncoder) {
    });


    @Test
    public void findAll() {
        List<User> usersFromMock = new ArrayList<>();
        Mockito.when(userRepository.findAll()).thenReturn(usersFromMock);
        List<User> users = testClass.findAll();
        assertEquals(users, usersFromMock);
        verify(testClass, times(1)).findAll();
    }

    @Test
    public void getById() {
        int entityId = 123;
        User expectedUser = new User();
        expectedUser.setUserId(entityId);
        when(userRepository.getUserByUserId(entityId)).thenReturn(expectedUser);

        User actualUser = testClass.getById(entityId);
        assertEquals(expectedUser, actualUser);
        verify(testClass, times(1)).getById(entityId);
    }

    @Test
    public void save() {
        User user = new User();
        user.setAge(12);
        user.setName("user");
        user.setPassword("password");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        testClass.save(user);

        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(argThat(savedUser -> savedUser.getName().equals("user") &&
                (savedUser.getAge() == 12) &&
                savedUser.getPassword().equals("encodedPassword")));
        verifyNoMoreInteractions(passwordEncoder, userRepository);
        verify(testClass, times(1)).save(user);
    }


    @Test
    public void deleteById() {
        int id = 1;
        testClass.deleteById(id);

        verify(userRepository, times(1)).deleteByUserId(id);
        verifyNoMoreInteractions(userRepository);
        verify(testClass, times(1)).deleteById(id);
    }

    @Test
    public void testFindByUsername() {
        String username = "name";
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(username);

        User expectedUser = new User();
        expectedUser.setName(username);
        when(userRepository.findByName(username)).thenReturn(expectedUser);

        User actualUser = testClass.findByUsername(principal);

        verify(userRepository, times(1)).findByName(username);
        verifyNoMoreInteractions(userRepository);

        assertNotNull(actualUser);
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        verify(testClass, times(1)).findByUsername(principal);
    }

    @Test
    public void findByUsernameWithNullPrincipal() {
        User actualUser = testClass.findByUsername(null);
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

        testClass.update(id, updatedUser);

        verify(userRepository, times(1)).getUserByUserId(id);
        verify(userRepository, times(1)).save(updatedUser);
        verifyNoMoreInteractions(userRepository);

        assertEquals(existingUser.getUsername(), updatedUser.getUsername());
        assertEquals(existingUser.getPassword(), updatedUser.getPassword());
        verify(testClass, times(1)).update(id, updatedUser);
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

        testClass.update(id, updatedUser);

        verify(userRepository, times(1)).getUserByUserId(id);
        verify(userRepository, times(1)).save(updatedUser);
        verifyNoMoreInteractions(userRepository);

        assertEquals(existingUser.getUsername(), updatedUser.getUsername());
        assertNotEquals(existingUser.getPassword(), updatedUser.getPassword());
        assertEquals(newPassword, updatedUser.getPassword());
        verify(testClass, times(1)).update(id, updatedUser);
    }

    @Test
    public void passwordCoder() {
        User user = new User();
        user.setUserId(1);
        user.setPassword("password");

        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        User result = testClass.passwordCoder(user);

        verify(passwordEncoder, times(1)).encode("password");
        verifyNoMoreInteractions(passwordEncoder);

        assertEquals("encodedPassword", result.getPassword());
        verify(testClass, times(1)).passwordCoder(user);

    }
}
