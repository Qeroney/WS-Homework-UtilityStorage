package thewhite.homework.api.statistics.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StatisticsDto {

    Long id;

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
