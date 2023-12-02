package thewhite.homework.api.grade.dto;

import lombok.*;

@Value
@Builder
public class SearchGradeDto {

    Integer rating;

    Long entryId;
}
