package thewhite.homework.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
