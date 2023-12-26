package thewhite.homework.service.statistics;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import thewhite.homework.model.Statistics;
import thewhite.homework.repository.StatisticsRepository;
import thewhite.homework.service.statistics.argument.UpdateStatisticsArgument;

import static org.mockito.ArgumentMatchers.any;

class StatisticsServiceTest {

    private final StatisticsRepository statisticsRepository = Mockito.mock(StatisticsRepository.class);

    private final StatisticsService service = new StatisticsServiceImpl(statisticsRepository);

    @Test
    void update() {
        //Arrange
        Statistics statistics = Mockito.mock(Statistics.class);
        UpdateStatisticsArgument argument = UpdateStatisticsArgument.builder()
                                                                    .totalEntries(5L)
                                                                    .totalGrades(5L)
                                                                    .noLessThanFourPercentage(1.1)
                                                                    .maxRatingEntries(5L)
                                                                    .maxRatingPercentage(1.1)
                                                                    .averageRating(5.0)
                                                                    .aboveFourEntries(5L)
                                                                    .aboveFourPercentage(5.0)
                                                                    .entriesWithoutGrades(5L)
                                                                    .noLessThanFourEntries(5L)
                                                                    .build();
        Mockito.when(statisticsRepository.save(any())).thenReturn(statistics);
        Mockito.when(statisticsRepository.findFirstBy()).thenReturn(Statistics.builder()
                                                                                       .totalEntries(5L)
                                                                                       .totalGrades(5L)
                                                                                       .noLessThanFourPercentage(1.1)
                                                                                       .maxRatingEntries(5L)
                                                                                       .maxRatingPercentage(1.1)
                                                                                       .averageRating(5.0)
                                                                                       .aboveFourEntries(5L)
                                                                                       .aboveFourPercentage(5.0)
                                                                                       .entriesWithoutGrades(5L)
                                                                                       .noLessThanFourEntries(5L)
                                                                                       .build());

        //Act
        Statistics update = service.update(argument);

        //Assert
        ArgumentCaptor<Statistics> statisticsArgumentCaptor = ArgumentCaptor.forClass(Statistics.class);
        Mockito.verify(statisticsRepository).save(statisticsArgumentCaptor.capture());

        Statistics expectedStatistics = Statistics.builder()
                                                  .totalEntries(5L)
                                                  .totalGrades(5L)
                                                  .noLessThanFourPercentage(1.1)
                                                  .maxRatingEntries(5L)
                                                  .maxRatingPercentage(1.1)
                                                  .averageRating(5.0)
                                                  .aboveFourEntries(5L)
                                                  .aboveFourPercentage(5.0)
                                                  .entriesWithoutGrades(5L)
                                                  .noLessThanFourEntries(5L)
                                                  .build();
        Assertions.assertThat(update).isEqualTo(statistics);
        Assertions.assertThat(statisticsArgumentCaptor.getValue())
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedStatistics);
    }
}
