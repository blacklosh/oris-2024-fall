package ru.itis.mapper;

import ru.itis.dto.MessageDto;
import ru.itis.model.MessageEntity;

public interface MessageMapper {

    MessageDto toDto(MessageEntity entity);
}
