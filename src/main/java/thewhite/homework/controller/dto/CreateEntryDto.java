package thewhite.homework.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
@Schema(description = "ДТО для создания записи")
public class CreateEntryDto {
    String name;
    String description;
    String link;
}
