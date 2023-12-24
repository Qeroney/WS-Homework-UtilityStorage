package thewhite.homework.action.grade.statistics;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import thewhite.homework.event.StatisticsEvent;
import thewhite.homework.model.Statistics;
import thewhite.homework.service.entry.EntryService;
import thewhite.homework.service.grade.GradeService;
import thewhite.homework.service.statistics.StatisticsService;
import thewhite.homework.service.statistics.argument.UpdateStatisticsArgument;

import static org.mockito.ArgumentMatchers.any;

class StatisticsActionTest {

    private final EntryService entryService = Mockito.mock(EntryService.class);

    private final GradeService gradeService = Mockito.mock(GradeService.class);

    private final StatisticsService statisticsService = Mockito.mock(StatisticsService.class);

    private final StatisticsAction action = new StatisticsAction(entryService, gradeService, statisticsService);

    @Test
    void statisticsListener() {
        //Arrange
        StatisticsEvent event = new StatisticsEvent("Stats");
        Statistics statistics = Statistics.builder()
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

        Mockito.when(entryService.getTotalEntries()).thenReturn(5L);
        Mockito.when(entryService.getAboveFourEntries()).thenReturn(5L);
        Mockito.when(entryService.percentageAboveFourEntries()).thenReturn(5.0);
        Mockito.when(entryService.getEntriesWithoutGrades()).thenReturn(5L);
        Mockito.when(gradeService.getTotalGrades()).thenReturn(5L);
        Mockito.when(gradeService.getAverageRating()).thenReturn(5.0);
        Mockito.when(entryService.getNoLessThanFourEntries()).thenReturn(5L);
        Mockito.when(entryService.entriesWithAverageGradeEqualsFive()).thenReturn(5L);
        Mockito.when(entryService.percentageEntriesWithAverageGradeEqualsFive()).thenReturn(1.1);
        Mockito.when(entryService.percentageNoLessThanFourEntries()).thenReturn(1.1);

        Mockito.when(statisticsService.update(any())).thenReturn(statistics);

        //Act
        action.statisticsListener(event);

        //Assert
        ArgumentCaptor<UpdateStatisticsArgument> updateStatisticsArgumentArgumentCaptor = ArgumentCaptor.forClass(UpdateStatisticsArgument.class);
        Mockito.verify(statisticsService).update(updateStatisticsArgumentArgumentCaptor.capture());

        UpdateStatisticsArgument expectedArgument = UpdateStatisticsArgument.builder()
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
        Assertions.assertThat(updateStatisticsArgumentArgumentCaptor.getValue())
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedArgument);

    }
}
