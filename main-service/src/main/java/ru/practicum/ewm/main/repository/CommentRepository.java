package ru.practicum.ewm.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.main.model.comment.Comment;
import ru.practicum.ewm.main.model.event.Event;
import ru.practicum.ewm.main.model.users.User;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getCommentsByUser(User user);

    List<Comment> getCommentsByEvent(Event event);

    Comment findByIdAndUserId(Long commentId, Long userId);

}
