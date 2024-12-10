package ru.itis.service.impl;

import lombok.RequiredArgsConstructor;
import ru.itis.dto.ChatDto;
import ru.itis.dto.MessageDto;
import ru.itis.dto.MessageSendingDto;
import ru.itis.dto.UserDataResponse;
import ru.itis.mapper.ChatMapper;
import ru.itis.mapper.MessageMapper;
import ru.itis.mapper.UserMapper;
import ru.itis.model.MessageEntity;
import ru.itis.repository.ChatRepository;
import ru.itis.service.ChatsService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ChatsServiceImpl implements ChatsService {

    private final ChatRepository chatRepository;

    private final ChatMapper chatMapper;
    private final UserMapper userMapper;
    private final MessageMapper messageMapper;

    @Override
    public Optional<ChatDto> findChatById(Long chatId) {
        return chatRepository.findById(chatId).map(chatMapper::toDto);
    }

    @Override
    public boolean isUserInChat(Long userId, Long chatId) {
        return findAllUsersInChat(chatId).stream().anyMatch(c -> c.getId().equals(userId));
    }

    @Override
    public List<ChatDto> findAllChatsByUserId(Long userId) {
        return chatRepository.findAllByUserId(userId).stream()
                .map(chatMapper::toDto)
                .toList();
    }

    @Override
    public List<UserDataResponse> findAllUsersInChat(Long chatId) {
        return chatRepository.findAllUsersInChat(chatId).stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public List<MessageDto> findAllMessagesInChat(Long chatId) {
        return chatRepository.findAllMessagesInChat(chatId).stream()
                .map(messageMapper::toDto)
                .toList();
    }

    @Override
    public MessageDto sendNewMessage(MessageSendingDto dto) {
        return messageMapper.toDto(chatRepository.saveNewMessage(MessageEntity.builder()
                .chatId(dto.getChatId())
                .authorId(dto.getUserId())
                .text(dto.getText())
                .build()));
    }
}
