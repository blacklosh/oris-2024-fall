package ru.itis.service;

import ru.itis.dto.ChatDto;
import ru.itis.dto.MessageDto;
import ru.itis.dto.MessageSendingDto;
import ru.itis.dto.UserDataResponse;

import java.util.List;

public interface ChatsService {

    List<ChatDto> findAllChatsByUserId(Long userId);

    List<UserDataResponse> findAllUsersInChat(Long chatId);

    List<MessageDto> findAllMessagesInChat(Long chatId);

    MessageDto sendNewMessage(MessageSendingDto dto);

}
