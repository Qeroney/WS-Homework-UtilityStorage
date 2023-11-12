package thewhite.homework.api.grade.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class GradeDto {

    UUID id;
    Long entryId;
    String comment;
    Integer rating;
}
