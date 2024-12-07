package ru.itis.mapper.impl;

import ru.itis.dto.ChatDto;
import ru.itis.mapper.ChatMapper;
import ru.itis.model.ChatEntity;

public class ChatMapperImpl implements ChatMapper {
    @Override
    public ChatDto toDto(ChatEntity entity) {
        return ChatDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .build();
    }
}
