package com.task_manager.security;

import com.task_manager.domain.AuthResponse;
import com.task_manager.domain.LoginRequest;
import com.task_manager.domain.RegisterRequest;
import com.task_manager.entities.AuthorEntity;
import com.task_manager.entities.UserEntity;
import com.task_manager.enums.Role;
import com.task_manager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthResponse login(LoginRequest request) {
        return null;
    }

    public AuthResponse register(RegisterRequest request) {

        UserEntity user = new UserEntity(
                null,
                request.email(),
                request.password(),
                new AuthorEntity(null, request.nick())
                Role.USER
        );

        userRepository.save(user);

        return new AuthResponse(jwtService.getToken(user));
    }
}
