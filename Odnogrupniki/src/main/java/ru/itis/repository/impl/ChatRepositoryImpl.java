package ru.itis.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.model.ChatEntity;
import ru.itis.model.MessageEntity;
import ru.itis.model.UserEntity;
import ru.itis.repository.ChatRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_CHAT_BY_ID = "select * from chat where id = ?";
    private static final String SQL_SELECT_ALL_CHATS_BY_USER_ID = "select c.* from chat c join chat_users cu on c.id = cu.chat_id where user_id = ?";
    private static final String SQL_SELECT_ALL_USERS_FROM_CHAT = "select u.* from user_data u join chat_users cu on u.id = cu.user_id where chat_id = ?";
    private static final String SQL_SELECT_ALL_MESSAGES_FROM_CHAT = "select * from messages where chat_id = ? order by datetime";
    private static final String SQL_INSERT = "insert into messages(chat_id, author_id, text) values (?, ?, ?)";

    private final ChatRowMapper chatRowMapper = new ChatRowMapper();
    private final UserRowMapper userRowMapper = new UserRowMapper();
    private final MessageRowMapper messageRowMapper = new MessageRowMapper();
    @Override
    public Optional<ChatEntity> findById(Long chatId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_CHAT_BY_ID, chatRowMapper, chatId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<ChatEntity> findAllByUserId(Long userId) {
        return jdbcTemplate.query(SQL_SELECT_ALL_CHATS_BY_USER_ID, chatRowMapper, userId);
    }

    @Override
    public List<UserEntity> findAllUsersInChat(Long chatId) {
        return jdbcTemplate.query(SQL_SELECT_ALL_USERS_FROM_CHAT, userRowMapper, chatId);
    }

    @Override
    public List<MessageEntity> findAllMessagesInChat(Long chatId) {
        return jdbcTemplate.query(SQL_SELECT_ALL_MESSAGES_FROM_CHAT, messageRowMapper, chatId);
    }

    @Override
    public MessageEntity saveNewMessage(MessageEntity message) {
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(SQL_INSERT, new String[] {"id"});
                ps.setLong(1, message.getChatId());
                ps.setLong(2, message.getAuthorId());
                ps.setString(3, message.getText());
                return ps;
            }, holder);
            Long id = Objects.requireNonNull(holder.getKey()).longValue();
            message.setId(id);
            return message;
        } catch (Exception e) {
            return null;
        }
    }

    private static final class ChatRowMapper implements RowMapper<ChatEntity> {

        @Override
        public ChatEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return ChatEntity.builder()
                    .id(rs.getLong("id"))
                    .title(rs.getString("title"))
                    .build();
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

    private static final class MessageRowMapper implements RowMapper<MessageEntity> {

        @Override
        public MessageEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            return MessageEntity.builder()
                    .id(rs.getLong("id"))
                    .text(rs.getString("text"))
                    .chatId(rs.getLong("chat_id"))
                    .authorId(rs.getLong("author_id"))
                    .build();
        }
    }
}
