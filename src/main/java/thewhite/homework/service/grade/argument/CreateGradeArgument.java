package thewhite.homework.service.grade.argument;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class CreateGradeArgument {
    Long entryId;
    String comment;
    Integer rating;
}
