package com.example.Security_REST.services;

import com.example.Security_REST.models.User;
import com.example.Security_REST.security.userDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersDetailService implements UserDetailsService {
    private final UsersService usersService;

    @Autowired
    public UsersDetailService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = usersService.findByUsername(s);
        if (user.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return new userDetails(user.get());
    }

}
