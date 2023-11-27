package thewhite.homework.service.grade.argument;

import lombok.Builder;
import lombok.Value;
import thewhite.homework.model.Entry;

@Value
@Builder
public class CreateGradeArgument {

    Entry entry;

    Long entryId;

    String comment;

    Integer rating;
}
