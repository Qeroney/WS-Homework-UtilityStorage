package thewhite.homework.api.grade.dto;

import lombok.*;

@Value
@Builder
public class CreateGradeDto {

    Long entryId;

    String comment;

    Integer rating;
}
