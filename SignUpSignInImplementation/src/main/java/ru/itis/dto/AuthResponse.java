package ru.itis.dto;

import lombok.*;
import ru.itis.model.UserEntity;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private int status;

    private String statusDesc;

    private UserEntity user;

}
