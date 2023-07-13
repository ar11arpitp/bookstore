package com.online.bookstore.service.impl;

import com.online.bookstore.entity.User;
import com.online.bookstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository mockRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsServiceUnderTest;
    private User user;
    @BeforeEach
    void setup(){
        List<String> authorities = new ArrayList<String>();
        authorities.add("User");
        authorities.add("Admin");
        user = new User();
        user.setId(1);
        user.setUsername("testUser");
        user.setAuthorities(authorities);
    }
    @Test
    void testLoadUserByUsername() {
        // Setup
        final User expectedResult = User.builder().build();
        when(mockRepository.findByUsername("username")).thenReturn(Optional.of(user));

        // Run the test
        final User result = customUserDetailsServiceUnderTest.loadUserByUsername("username");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testLoadUserByUsername_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockRepository.findByUsername("username")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> customUserDetailsServiceUnderTest.loadUserByUsername("username"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
