package thewhite.homework.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Value
@Builder
@Schema(description = "ДТО для обновления записи")
public class UpdateEntryDto {
    String name;
    String description;
    String link;
}
