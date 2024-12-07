package ru.itis.mapper;

import ru.itis.dto.ChatDto;
import ru.itis.model.ChatEntity;

public interface ChatMapper {

    ChatDto toDto(ChatEntity entity);

}
