package thewhite.homework.service.grade.argument;

import lombok.*;

@Value
@Builder
public class SearchGradeArgument {

    Integer rating;

    Long entryId;
}
