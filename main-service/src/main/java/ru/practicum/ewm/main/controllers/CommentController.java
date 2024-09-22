package ru.practicum.ewm.main.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<CommentDto>> getCommentsByEvent(@PathVariable("eventId") Long eventId) {
        return ResponseEntity.ok().body(commentService.getCommentsByEvent(eventId));
    }

    @PostMapping("/users/{userId}/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto addComment(@Valid @RequestBody CommentDto commentDto,
                                 @PathVariable("userId") Long userId,
                                 @PathVariable("eventId") Long eventId) {
        return commentService.addComment(userId, eventId, commentDto);
    }

    @PatchMapping("/users/{userId}/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto patchComment(@Valid @RequestBody CommentDto commentDto,
                                   @PathVariable("userId") Long userId,
                                   @PathVariable("commentId") Long commentId) {
        return commentService.patchComment(userId, commentId, commentDto);
    }

    @DeleteMapping("/users/{userId}/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("userId") Long userId,
                              @PathVariable("commentId") Long commentId) {
        commentService.deleteComment(userId, commentId);
    }
}
