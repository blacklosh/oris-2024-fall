package ru.itis.mapper;

import ru.itis.dto.SignUpRequest;
import ru.itis.dto.UserDataResponse;
import ru.itis.model.UserEntity;

public interface UserMapper {

    UserEntity toEntity(SignUpRequest request);

    UserDataResponse toDto(UserEntity entity);

}
