package thewhite.homework.service.grade.argument;

import lombok.*;
import thewhite.homework.model.Entry;

@Value
@Builder
public class CreateGradeArgument {

    Entry entry;

    String comment;

    Integer rating;
}
