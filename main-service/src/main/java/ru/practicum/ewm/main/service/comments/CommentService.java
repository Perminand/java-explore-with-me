package ru.practicum.ewm.main.service.comments;

import ru.practicum.ewm.main.dto.comment.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getCommentsByUser(Long userId);

    CommentDto patchComment(Long commentId, CommentDto commentDto);

    void deleteComment(Long commentId);

    CommentDto addComment(Long userId, Long eventId, CommentDto commentDto);

    CommentDto patchComment(Long userId, Long commentId, CommentDto commentDto);

    void deleteComment(Long userId, Long commentId);

    List<CommentDto> getCommentsByEvent(Long eventId);

}
