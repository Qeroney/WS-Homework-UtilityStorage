package thewhite.homework.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Schema(description = "Класс ошибки")
public class MessageError {
    private String errorMessage;
}