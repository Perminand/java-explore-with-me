package ru.practicum.ewm.main.mappers;

import lombok.RequiredArgsConstructor;
import ru.practicum.ewm.main.common.Constants;
import ru.practicum.ewm.main.dto.comment.CommentDto;
import ru.practicum.ewm.main.model.comment.Comment;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.users.User;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CommentMapper {

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getText(),
                comment.getEvent().getId(),
                UserMapper.toShortDto(comment.getUser()), comment.getCreated().format(Constants.DATE_FORMATTER));
    }

    public static Comment toEntity(CommentDto dto, Event event, User user) {
        return new Comment(null, event, user, dto.getText(), LocalDateTime.parse(dto.getCreated(), Constants.DATE_FORMATTER));
    }
}
