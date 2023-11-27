package thewhite.homework.api.entry.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import thewhite.homework.model.Grade;

import java.util.List;

@Value
@Builder
@Schema(description = "ДТО для обновления записи")
public class UpdateEntryDto {

    String name;

    String description;

    List<String> links;

    List<Grade> grades;
}
