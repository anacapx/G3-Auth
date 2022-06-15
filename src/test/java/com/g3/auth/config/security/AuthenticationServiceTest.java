package com.g3.auth.config.security;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.g3.auth.model.Admin;
import com.g3.auth.repository.AdminRepository;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private AdminRepository repository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void testLoadUserByUsername() {
        // Given
        Admin admin = mock(Admin.class);
        Optional<Admin> adminOptional = Optional.of(admin);

        //When
        when(repository.findByEmail(admin.getUsername())).thenReturn(adminOptional);

        UserDetails userDetailsTest = authenticationService.loadUserByUsername(admin.getUsername());

        // Then
        verify(repository).findByEmail(admin.getUsername());
        assertNotNull(userDetailsTest);
    }

    @Test
    void testLoadUserByUsernameThrows() {
        // Given
        Admin admin = mock(Admin.class);

        //When
        when(repository.findByEmail(admin.getUsername())).thenThrow(UsernameNotFoundException.class);

        // Then
        assertThrows(UsernameNotFoundException.class, () -> authenticationService.loadUserByUsername(admin.getUsername()));
    }
}
