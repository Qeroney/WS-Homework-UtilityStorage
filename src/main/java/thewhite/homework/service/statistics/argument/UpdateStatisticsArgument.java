package thewhite.homework.service.statistics.argument;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateStatisticsArgument {

    Long totalEntries;

    Long totalGrades;

    Double averageRating;

    Long maxRatingEntries;

    Double maxRatingPercentage;

    Long aboveFourEntries;

    Double aboveFourPercentage;

    Long noLessThanFourEntries;

    Double noLessThanFourPercentage;

    Long entriesWithoutGrades;
}
