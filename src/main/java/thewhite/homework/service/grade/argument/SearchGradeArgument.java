package thewhite.homework.service.grade.argument;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SearchGradeArgument {

    Integer rating;
}
