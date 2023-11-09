package thewhite.homework.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor(staticName = "of")
@Schema(description = "Класс ошибки")
public class MessageError {
    private final String errorMessage;
}