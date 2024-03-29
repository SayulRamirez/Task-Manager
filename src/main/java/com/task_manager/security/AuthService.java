package com.task_manager.security;

import com.task_manager.domain.AuthResponse;
import com.task_manager.domain.LoginRequest;
import com.task_manager.domain.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    public AuthResponse login(LoginRequest request) {
        return null;
    }

    public AuthResponse register(RegisterRequest request) {
        return null;
    }
}
