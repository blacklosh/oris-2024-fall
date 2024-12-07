package ru.itis.mapper.impl;

import lombok.RequiredArgsConstructor;
import ru.itis.dto.MessageDto;
import ru.itis.mapper.MessageMapper;
import ru.itis.model.MessageEntity;
import ru.itis.repository.UserRepository;

@RequiredArgsConstructor
public class MessageMapperImpl implements MessageMapper {

    private final UserRepository userRepository;
    @Override
    public MessageDto toDto(MessageEntity entity) {
        return MessageDto.builder()
                .text(entity.getText())
                .authorNickname(userRepository.findUserById(entity.getAuthorId())
                        .get().getNickname())
                .build();
    }
}
