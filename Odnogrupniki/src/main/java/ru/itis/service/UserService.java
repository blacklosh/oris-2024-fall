package ru.itis.service;

import ru.itis.dto.AuthResponse;
import ru.itis.dto.SignInRequest;
import ru.itis.dto.SignUpRequest;

public interface UserService {

    AuthResponse signUp(SignUpRequest request);

    AuthResponse signIn(SignInRequest request);

}
