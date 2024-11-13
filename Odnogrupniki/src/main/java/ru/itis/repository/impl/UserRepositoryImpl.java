package ru.itis.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.config.ModuleConfiguration;
import ru.itis.model.UserEntity;
import ru.itis.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate = ModuleConfiguration.jdbcTemplate();

    private static final String SQL_SELECT_BY_ID = "select * from user_data where id = ?";

    private static final String SQL_SELECT_BY_EMAIL = "select * from user_data where email = ?";

    private static final String SQL_SELECT_BY_NICKNAME = "select * from user_data where nickname = ?";

    private static final String SQL_INSERT = "insert into user_data(email, hash_password, nickname) values (?, ?, ?)";

    private final UserRowMapper userRowMapper = new UserRowMapper();

    @Override
    public Optional<UserEntity> findUserById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new Object[] {id}, userRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findUserByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, new Object[] {email}, userRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> findUserByNickname(String nickname) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_NICKNAME, new Object[] {nickname}, userRowMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserEntity> saveNewUser(UserEntity user) {
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_INSERT, new String[] {"id"});
                ps.setString(1, user.getEmail());
                ps.setString(2, user.getHashPassword());
                ps.setString(3, user.getNickname());
                return ps;
            }, holder);
            Long id = Objects.requireNonNull(holder.getKey()).longValue();
            return findUserById(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static final class UserRowMapper implements RowMapper<UserEntity> {

        @Override
        public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return UserEntity.builder()
                    .id(rs.getLong("id"))
                    .email(rs.getString("email"))
                    .nickname(rs.getString("nickname"))
                    .hashPassword(rs.getString("hash_password"))
                    .build();
        }
    }
}
