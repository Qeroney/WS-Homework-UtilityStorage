package thewhite.homework.service.grade;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GradeStatistics {

    Long totalGrades;

    Double averageRating;
}
