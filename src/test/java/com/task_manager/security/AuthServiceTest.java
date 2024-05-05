package com.task_manager.security;


import com.task_manager.domain.AuthResponse;
import com.task_manager.domain.LoginRequest;
import com.task_manager.domain.RegisterRequest;
import com.task_manager.entities.AuthorEntity;
import com.task_manager.entities.UserEntity;
import com.task_manager.enums.Role;
import com.task_manager.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void loginError() {
        LoginRequest request = new LoginRequest("example@mail.com", "secret");

        given(userRepository.findByUsername(request.email())).willReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> authService.login(request));
    }

    @Test
    void register() {

        RegisterRequest register = new RegisterRequest("example@mail.com", "secret", "marco");

        given(passwordEncoder.encode(any(CharSequence.class))).willAnswer(invocation -> invocation.getArgument(0));

        given(jwtService.getToken(any(UserDetails.class))).willCallRealMethod();

        AuthResponse response = authService.register(register);

        assertThat(response.token()).isNotNull();
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void login() {

        RegisterRequest register = new RegisterRequest("example@mail.com", "secret", "marco");

        LoginRequest request = new LoginRequest(register.email(), register.password());

        UserEntity userEntity = new UserEntity(
                1L, register.email(), register.password(),
                new AuthorEntity(1L, register.nick()),
                Role.USER
        );

        given(userRepository.findByUsername(request.email())).willReturn(Optional.of(userEntity));

        given(jwtService.getToken(any(UserDetails.class))).willCallRealMethod();

        AuthResponse login = authService.login(request);

        assertThat(login.token()).isNotNull();
        verify(authenticationManager, times(1)).authenticate(any(Authentication.class));
        verify(userRepository, times(1)).findByUsername(any(String.class));
        verify(jwtService, times(1)).getToken(any(UserDetails.class));
    }
}