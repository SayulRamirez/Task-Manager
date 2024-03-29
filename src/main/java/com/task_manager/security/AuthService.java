package com.task_manager.security;

import com.task_manager.domain.AuthResponse;
import com.task_manager.domain.LoginRequest;
import com.task_manager.domain.RegisterRequest;
import com.task_manager.entities.AuthorEntity;
import com.task_manager.entities.UserEntity;
import com.task_manager.enums.Role;
import com.task_manager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        UserDetails user = userRepository.findByUsername(request.email()).orElseThrow();

        String token = jwtService.getToken(user);

        return new AuthResponse(token);
    }

    public AuthResponse register(RegisterRequest request) {

        UserEntity user = new UserEntity(
                null,
                request.email(),
                passwordEncoder.encode(request.password()),
                new AuthorEntity(null, request.nick()),
                Role.USER
        );

        userRepository.save(user);

        return new AuthResponse(jwtService.getToken(user));
    }
}
