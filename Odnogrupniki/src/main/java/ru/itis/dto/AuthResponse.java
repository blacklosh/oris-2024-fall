package ru.itis.dto;

import lombok.*;
import ru.itis.annotation.MyAnnotation;
import ru.itis.exception.MyCheckedException;
import ru.itis.exception.MyUncheckedException;

@Data
@Builder
@ToString
@AllArgsConstructor
public class AuthResponse {

    private int status;

    private String statusDesc;

    private UserDataResponse user;

}
