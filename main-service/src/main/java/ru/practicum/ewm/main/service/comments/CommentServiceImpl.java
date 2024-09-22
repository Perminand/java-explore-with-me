package ru.practicum.ewm.main.service.comments;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.dto.comment.CommentDto;
import ru.practicum.ewm.main.exceptions.errors.EntityNotFoundException;
import ru.practicum.ewm.main.exceptions.errors.ValidationException;
import ru.practicum.ewm.main.mappers.CommentMapper;
import ru.practicum.ewm.main.model.comment.Comment;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.users.User;
import ru.practicum.ewm.main.repository.CommentRepository;
import ru.practicum.ewm.main.repository.EventRepository;
import ru.practicum.ewm.main.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;

    @Override
    public List<CommentDto> getCommentsByUser(Long userId) {
        User user = getUserById(userId);
        return commentRepository.getCommentsByUser(user).stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto addComment(Long userId, Long eventId, CommentDto commentDto) {
        User user = getUserById(userId);
        Event event = getEventById(eventId);
        Comment comment = CommentMapper.toEntity(commentDto, event, user);
        comment.setCreated(LocalDateTime.now());
        comment.setEvent(event);
        comment.setUser(user);
        return CommentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentDto> getCommentsByEvent(Long eventId) {
        return commentRepository.getCommentsByEvent(getEventById(eventId)).stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentByUser(Long userId, Long commentId) {
        getUserById(userId);
        return CommentMapper.toDto(commentRepository.findByIdAndUserId(commentId, userId));
    }

    @Override
    public CommentDto patchComment(Long userId, Long commentId, CommentDto commentDto) {
        Comment comment;
        if (userId == null) {
            comment = getCommentById(commentId);
        } else {
            comment = getCommentByIdAndUserId(commentId, getUserById(userId).getId());
        }
        comment.setText(commentDto.getText());
        return CommentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long userId, Long commentId) {
        if (userId == null) {
            commentRepository.deleteById(commentId);
        } else {
            commentRepository.deleteById(getCommentByIdAndUserId(commentId, getUserById(userId).getId()).getId());
        }
    }

    private Comment getCommentByIdAndUserId(Long commentId, Long userId) {
        Comment comment = getCommentById(commentId);
        if (!Objects.equals(userId, comment.getUser().getId())) {
            throw new ValidationException("Пользователь не является автором комментария");
        }
        return comment;
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь с ид: " + userId + " не найден."));
    }

    private Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Событие с ид: " + eventId + " не найдено."));
    }

    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("комментарий с ид: " + commentId + " не найден."));
    }

}
