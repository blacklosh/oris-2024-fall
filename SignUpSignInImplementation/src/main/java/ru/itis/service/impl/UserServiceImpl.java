package ru.itis.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.config.ModuleConfiguration;
import ru.itis.dto.AuthResponse;
import ru.itis.dto.SignInRequest;
import ru.itis.dto.SignUpRequest;
import ru.itis.model.UserEntity;
import ru.itis.service.UserService;
import ru.itis.util.AuthUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class UserServiceImpl implements UserService {

    private final JdbcTemplate jdbcTemplate = ModuleConfiguration.jdbcTemplate();

    private final UserRowMapper userRowMapper = new UserRowMapper();

    private static final String SQL_SELECT_BY_ID = "select * from user_data where id = ?";

    private static final String SQL_SELECT_BY_EMAIL = "select * from user_data where email = ?";

    private static final String SQL_SELECT_BY_TOKEN = "select * from user_data where token = ?";

    private static final String SQL_SELECT_BY_NICKNAME = "select * from user_data where nickname = ?";

    private static final String SQL_INSERT = "insert into user_data(email, hash_password, nickname, token, token_usage) values (?, ?, ?, ?, 3)";

    private static final String SQL_UPDATE = "update user_data set token_usage = ? where id = ?";

    private static final String SQL_UPDATE_TOKEN = "update user_data set token_usage = 3, token = ? where id = ?";

    @Override
    public UserEntity findUserById(Long id) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[] {id}, userRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public UserEntity findUserByEmail(String email) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, new Object[] {email}, userRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public UserEntity findUserByNickname(String nickname) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_BY_NICKNAME, new Object[] {nickname}, userRowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public AuthResponse signUp(SignUpRequest request) {
        if(request.getEmail() == null || request.getEmail().isBlank())
            return response(1, "Empty email", null);

        if(request.getPassword() == null || request.getPassword().isBlank())
            return response(2, "Empty password", null);

        if(request.getNickname() == null || request.getNickname().isBlank())
            return response(3, "Empty nickname", null);

        if(!AuthUtils.checkEmail(request.getEmail()))
            return response(4, "Invalid email", null);

        if(!AuthUtils.checkPassword(request.getPassword()))
            return response(5, "Weak password", null);

        if(findUserByEmail(request.getEmail()) != null)
            return response(6, "Email taken", null);

        if(findUserByNickname(request.getNickname()) != null)
            return response(7, "Nickname taken", null);

        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_INSERT, new String[] {"id"});
            ps.setString(1, request.getEmail());
            ps.setString(2, AuthUtils.hashPassword(request.getPassword()));
            ps.setString(3, request.getNickname());
            ps.setString(4, UUID.randomUUID().toString());
            return ps;
        }, holder);
        Long id = Objects.requireNonNull(holder.getKey()).longValue();
        return response(0, "OK", findUserById(id));
    }

    @Override
    public AuthResponse signIn(SignInRequest request) {
        if(request.getEmail() == null || request.getEmail().isBlank())
            return response(1, "Empty email", null);

        if(request.getPassword() == null || request.getPassword().isBlank())
            return response(2, "Empty password", null);

        if(!AuthUtils.checkEmail(request.getEmail()))
            return response(4, "Invalid email", null);

        UserEntity user = findUserByEmail(request.getEmail());

        if(user == null)
            return response(8, "Email not found", null);

        if(!AuthUtils.verifyPassword(request.getPassword(), user.getHashPassword()))
            return response(9, "Password mismatch", null);

        jdbcTemplate.update(SQL_UPDATE_TOKEN, UUID.randomUUID().toString(), user.getId());

        return response(0, "OK", findUserByEmail(request.getEmail()));
    }

    @Override
    public AuthResponse signInByToken(String token) {
        try {
            UserEntity user = jdbcTemplate.queryForObject(SQL_SELECT_BY_TOKEN, new Object[] {token}, userRowMapper);
            if(user == null)
                return response(10, "Token not found", null);
            if(user.getToken() == null || user.getTokenUsage() < 1)
                return response(11, "Token expired", null);

            jdbcTemplate.update(SQL_UPDATE, user.getTokenUsage() - 1, user.getId());

            return response(0, "OK", findUserById(user.getId()));
        } catch (EmptyResultDataAccessException e) {
            return response(10, "Token not found", null);
        }
    }

    private AuthResponse response(int status, String statusDesc, UserEntity user) {
        return AuthResponse.builder()
                .status(status)
                .statusDesc(statusDesc)
                .user(user)
                .build();
    }

    private static final class UserRowMapper implements RowMapper<UserEntity> {

        @Override
        public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return UserEntity.builder()
                    .id(rs.getLong("id"))
                    .email(rs.getString("email"))
                    .nickname(rs.getString("nickname"))
                    .hashPassword(rs.getString("hash_password"))
                    .token(rs.getString("token"))
                    .tokenUsage(rs.getInt("token_usage"))
                    .build();
        }
    }
}
