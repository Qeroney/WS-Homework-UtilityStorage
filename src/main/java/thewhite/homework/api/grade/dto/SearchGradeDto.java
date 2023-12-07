package thewhite.homework.api.grade.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Value
@Builder
public class SearchGradeDto {

    Integer rating;

    @NotNull(message = "не указан id записи")
    Long entryId;
}
