package ru.itis.mapper.impl;

import lombok.extern.slf4j.Slf4j;
import ru.itis.dto.SignUpRequest;
import ru.itis.dto.UserDataResponse;
import ru.itis.mapper.UserMapper;
import ru.itis.model.UserEntity;
import ru.itis.util.AuthUtils;

@Slf4j
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity toEntity(SignUpRequest request) {
        return UserEntity.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .hashPassword(AuthUtils.hashPassword(request.getPassword()))
                .build();
    }

    @Override
    public UserDataResponse toDto(UserEntity entity) {
        return UserDataResponse.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .nickname(entity.getNickname())
                .build();
    }
}
