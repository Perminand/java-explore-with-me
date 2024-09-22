package ru.practicum.ewm.main.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.dto.comment.CommentDto;
import ru.practicum.ewm.main.service.comments.CommentService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{eventId}")
    public List<CommentDto> getCommentsByEvent(@PathVariable("eventId") Long eventId) {
        log.info("GET запрос на на получение коммента по ид события");
        return commentService.getCommentsByEvent(eventId);
    }

    @GetMapping("/users/{userId}/commentId/{commentId}")
    public CommentDto getCommentsByUser(
            @Min(0) @PathVariable("userId") Long userId,
            @Min(0) @PathVariable("commentId") Long commentId) {
        log.info("GET запрос на на получение коммента по ид события");
        return commentService.getCommentByUser(userId, commentId);
    }

    @PostMapping("/users/{userId}/events/{eventId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addComment(@Min(0) @PathVariable("userId") Long userId,
                                 @Min(0) @PathVariable("eventId") Long eventId,
                                 @Valid @RequestBody CommentDto commentDto) {
        log.info("Post запрос на на создание коммента");
        return commentService.addComment(userId, eventId, commentDto);
    }

    @PatchMapping("/users/{userId}/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto patchComment(@Valid @RequestBody CommentDto commentDto,
                                   @PathVariable("userId") Long userId,
                                   @PathVariable("commentId") Long commentId) {
        log.info("Patch запрос на на изменение коммента");
        return commentService.patchComment(userId, commentId, commentDto);
    }

    @DeleteMapping("/users/{userId}/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("userId") Long userId,
                              @PathVariable("commentId") Long commentId) {
        log.info("Delete запрос на на удаление коммента");
        commentService.deleteComment(userId, commentId);
    }
}
