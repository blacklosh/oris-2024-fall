package ru.itis.service;

import ru.itis.dto.AuthResponse;
import ru.itis.dto.SignInRequest;
import ru.itis.dto.SignUpRequest;

import java.util.UUID;

public interface UserService {

    AuthResponse signUp(SignUpRequest request);

    AuthResponse signIn(SignInRequest request);

    void setAvatar(Long userId, UUID fileId);

}
