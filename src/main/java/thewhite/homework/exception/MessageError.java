package thewhite.homework.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor(staticName = "of")
@Schema(description = "Класс ошибки")
@NoArgsConstructor
public class MessageError {
    private String errorMessage;
}