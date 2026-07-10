package com.auth.service;

import com.auth.dto.LoginRequest;
import com.auth.dto.LoginResponse;
import com.auth.entity.User;
import com.auth.jwt.JwtUtil;
import com.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final JwtUtil jwtUtil;

    public LoginResponse login(
            LoginRequest request) {

        User user = repository

                .findByUsername(
                        request.getUsername())

                .orElseThrow(
                        () -> new RuntimeException(
                                "Invalid User"));

        if (!user.getPassword()
                .equals(
                        request.getPassword())) {

            throw new RuntimeException(
                    "Invalid Password");
        }

        String token =

                jwtUtil.generateToken(
                        user.getUsername());

        return LoginResponse.builder()

                .token(token)

                .username(user.getUsername())

                .role(user.getRole())

                .customerId("CUST1001")

                .build();
    }
}
