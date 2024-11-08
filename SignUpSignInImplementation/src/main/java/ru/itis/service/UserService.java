package ru.itis.service;

import ru.itis.dto.AuthResponse;
import ru.itis.dto.SignInRequest;
import ru.itis.dto.SignUpRequest;
import ru.itis.model.UserEntity;

public interface UserService {

    UserEntity findUserById(Long id);

    UserEntity findUserByEmail(String email);

    UserEntity findUserByNickname(String nickname);

    AuthResponse signUp(SignUpRequest request);

    AuthResponse signIn(SignInRequest request);

    AuthResponse signInByToken(String token);

}
