package ru.itis.model;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private Long id;

    private String email;

    private String hashPassword;

    private String nickname;

}
