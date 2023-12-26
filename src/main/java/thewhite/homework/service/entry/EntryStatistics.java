package thewhite.homework.service.entry;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EntryStatistics {

    Long totalEntries;

    Long maxRatingEntries;

    Double maxRatingPercentage;

    Long aboveFourEntries;

    Double aboveFourPercentage;

    Long noLessThanFourEntries;

    Double noLessThanFourPercentage;

    Long entriesWithoutGrades;
}
