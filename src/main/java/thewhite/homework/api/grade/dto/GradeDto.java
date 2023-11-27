package thewhite.homework.api.grade.dto;

import lombok.*;

import java.util.UUID;

@Value
@Builder
public class GradeDto {

    UUID id;

    Long entryId;

    String comment;

    Integer rating;
}
