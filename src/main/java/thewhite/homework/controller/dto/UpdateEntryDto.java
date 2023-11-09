package thewhite.homework.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Value
@Builder
@Schema(description = "ДТО для обновления записи")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEntryDto {
    String name;
    String description;
    String link;
}
