package thewhite.homework.api.grade.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateGradeDto {

    Long entryId;
    String comment;
    Integer rating;
}
