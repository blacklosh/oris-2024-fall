package ru.itis.service;

import ru.itis.dto.ChatDto;
import ru.itis.dto.MessageDto;
import ru.itis.dto.MessageSendingDto;
import ru.itis.dto.UserDataResponse;

import java.util.List;
import java.util.Optional;

public interface ChatsService {

    Optional<ChatDto> findChatById(Long chatId);

    boolean isUserInChat(Long userId, Long chatId);

    List<ChatDto> findAllChatsByUserId(Long userId);

    List<UserDataResponse> findAllUsersInChat(Long chatId);

    List<MessageDto> findAllMessagesInChat(Long chatId);

    MessageDto sendNewMessage(MessageSendingDto dto);

}
