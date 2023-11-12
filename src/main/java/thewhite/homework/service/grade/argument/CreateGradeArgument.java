package thewhite.homework.service.grade.argument;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateGradeArgument {
    Long entryId;
    String comment;
    Integer rating;
}
