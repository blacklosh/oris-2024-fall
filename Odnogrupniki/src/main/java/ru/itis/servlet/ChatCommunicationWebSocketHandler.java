package ru.itis.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.itis.config.WebSocketConfigurer;
import ru.itis.dto.UserDataResponse;
import ru.itis.dto.WebSocketMessage;
import ru.itis.model.MessageEntity;
import ru.itis.model.UserEntity;
import ru.itis.repository.ChatRepository;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.*;

@Slf4j
@ServerEndpoint(value = "/ws", configurator = WebSocketConfigurer.class)
public class ChatCommunicationWebSocketHandler {

    private static Map<Long, Session> sessions = Collections.synchronizedMap(new HashMap<>());

    private ObjectMapper objectMapper = new ObjectMapper();

    public static ChatRepository chatRepository;

    @OnOpen
    public void handleOpen(EndpointConfig config, Session session) {
        UserDataResponse user = (UserDataResponse) config.getUserProperties().get("user");
        log.info("Подключился пользователь {}", user.getId());
        sessions.put(user.getId(), session);
    }

    @OnClose
    public void handleClose(Session session) {
        log.info("Отключился пользователь");
        sessions.values().remove(session);
    }

    @OnMessage
    @SneakyThrows
    public void handleMessage(String message, Session session) {
        log.info("Получил сообщение {}", message);
        WebSocketMessage webSocketMessage = objectMapper.readValue(message, WebSocketMessage.class);

        Long userId = findUserIdBySession(session);
        chatRepository.saveNewMessage(MessageEntity.builder()
                .chatId(webSocketMessage.getChatId())
                .authorId(userId)
                .text(webSocketMessage.getMessage())
                .build());

        List<UserEntity> usersInChat = chatRepository.findAllUsersInChat(webSocketMessage.getChatId());
        for(UserEntity user : usersInChat) {
            log.info("Проверяю пользователя {}", user.getId());
            if(sessions.containsKey(user.getId())) {
                log.info("Пользователь {} онлайн, отправляю сообщение", user.getId());
                sendMessage(sessions.get(user.getId()), message);
            }
        }
    }

    @OnError
    public void handleException(Throwable throwable) {
        log.error(throwable.getMessage());
    }

    @SneakyThrows
    private void sendMessage(Session session, String message) {
        session.getBasicRemote().sendText(message);
    }

    private Long findUserIdBySession(Session session) {
        Set<Map.Entry<Long, Session>> entrySet = sessions.entrySet();
        for(Map.Entry<Long, Session> pair : entrySet) {
            if(session.equals(pair.getValue())) {
                return pair.getKey();
            }
        }
        return null;
    }
}
