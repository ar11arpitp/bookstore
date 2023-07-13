package com.online.bookstore.service.impl;

import com.online.bookstore.entity.User;
import com.online.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    /**
     * Loads the user by username.
     *
     * @param username The username of the user to load.
     * @return The user entity.
     * @throws UsernameNotFoundException If the user is not found.
     */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
    }
}
