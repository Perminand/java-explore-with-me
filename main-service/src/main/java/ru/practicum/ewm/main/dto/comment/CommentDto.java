package ru.practicum.ewm.main.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.main.dto.user.UserShortDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CommentDto {
    @NotBlank
    @Length(min = 10, max = 280)
    private String text;
    private Long eventId;
    private UserShortDto authorId;
    private String created;
}
