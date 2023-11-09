package thewhite.homework.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "ДТО для создания записи")
public class CreateEntryDto {
    String name;
    String description;
    String link;
}
