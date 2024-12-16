package ru.itis.mapper.impl;

import lombok.RequiredArgsConstructor;
import ru.itis.dto.MessageDto;
import ru.itis.mapper.MessageMapper;
import ru.itis.model.MessageEntity;
import ru.itis.model.UserEntity;
import ru.itis.repository.UserRepository;

@RequiredArgsConstructor
public class MessageMapperImpl implements MessageMapper {

    private final UserRepository userRepository;

    @Override
    public MessageDto toDto(MessageEntity entity) {
        UserEntity user = userRepository.findUserById(entity.getAuthorId()).get();
        return MessageDto.builder()
                .text(entity.getText())
                .authorNickname(user.getNickname())
                .authorAvatarId(user.getAvatarId())
                .build();
    }
}
