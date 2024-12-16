package ru.itis.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.itis.config.WebSocketConfigurer;
import ru.itis.dto.WebSocketMessage;
import ru.itis.model.MessageEntity;
import ru.itis.model.UserEntity;
import ru.itis.repository.ChatRepository;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@ServerEndpoint(value = "/websocket", configurator = WebSocketConfigurer.class)
public class ChatWebSocketServlet {

    private static final Map<Long, Session> sessions = Collections.synchronizedMap(new HashMap<>());

    private static ChatRepository chatRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    public static void setChatRepository(ChatRepository chatRepository) {
        ChatWebSocketServlet.chatRepository = chatRepository;
    }

    @OnOpen
    public void handleOpen(EndpointConfig config, Session session) {
        log.warn("Connected user");
        Long userId = (Long) config.getUserProperties().get("userId");
        sessions.put(userId, session);
        log.info("Добавил в мапу пользователя {}", userId);
    }

    @OnMessage
    @SneakyThrows
    public void handleMessage(String message, Session session) {
        log.info("Получил сообщение {}", message);
        WebSocketMessage webSocketMessage = objectMapper.readValue(message, WebSocketMessage.class);
        chatRepository.saveNewMessage(MessageEntity.builder()
                .text(new String(webSocketMessage.getMessage().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1))
                .chatId(webSocketMessage.getChatId())
                .authorId(findUserIdBySession(session))
                .build());
        List<UserEntity> usersInChat = chatRepository.findAllUsersInChat(webSocketMessage.getChatId());
        log.warn("Got message for chat {}: {}", webSocketMessage.getChatId(), webSocketMessage.getMessage());
        for(UserEntity user : usersInChat) {
            log.info("Смотрю на пользователя {} в чате", user.getId());
            if(sessions.containsKey(user.getId())) {
                log.info("Отправил сообщение пользователю {}: {}", user.getId(), message);
                sendMessage(sessions.get(user.getId()), message);
            }
        }
    }

    @OnClose
    public void handleClose(Session session) {
        sessions.values().remove(session);
        log.info("Убрал из мапы пользователя");
    }

    @OnError
    public void handleError(Throwable throwable) {
        log.error(throwable.getMessage());
    }

    @SneakyThrows
    private void sendMessage(Session session, String message) {
        session.getBasicRemote().sendText(message);
    }

    private Long findUserIdBySession(Session session) {
        Set<Map.Entry<Long, Session>> entrySet = sessions.entrySet();
        for (Map.Entry<Long, Session> pair : entrySet) {
            if (session.equals(pair.getValue())) {
                return pair.getKey();
            }
        }
        return null;
    }

}
