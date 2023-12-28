package thewhite.homework.action.grade.statistics;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import thewhite.homework.event.StatisticsEvent;
import thewhite.homework.service.entry.EntryService;
import thewhite.homework.service.entry.EntryStatistics;
import thewhite.homework.service.grade.GradeService;
import thewhite.homework.service.grade.GradeStatistics;
import thewhite.homework.service.statistics.StatisticsService;
import thewhite.homework.service.statistics.argument.UpdateStatisticsArgument;

class StatisticsActionTest {

    private final EntryService entryService = Mockito.mock(EntryService.class);

    private final GradeService gradeService = Mockito.mock(GradeService.class);

    private final StatisticsService statisticsService = Mockito.mock(StatisticsService.class);

    private final StatisticsAction action = new StatisticsAction(entryService, gradeService, statisticsService);

    @Test
    void statisticsListener() {
        //Arrange
        StatisticsEvent event = new StatisticsEvent("Stats");
        EntryStatistics entryStatistics = EntryStatistics.builder()
                                                         .totalEntries(5L)
                                                         .maxRatingEntries(5L)
                                                         .entriesWithoutGrades(5L)
                                                         .aboveFourEntries(5L)
                                                         .aboveFourPercentage(1.1)
                                                         .maxRatingPercentage(1.1)
                                                         .noLessThanFourPercentage(1.1)
                                                         .noLessThanFourEntries(5L)
                                                         .build();
        GradeStatistics gradeStatistics = GradeStatistics.builder()
                                                         .averageRating(1.1)
                                                         .totalGrades(5L)
                                                         .build();

        Mockito.when(entryService.getEntryStatistics()).thenReturn(entryStatistics);
        Mockito.when(gradeService.getGradeStatistics()).thenReturn(gradeStatistics);

        //Act
        action.statisticsListener(event);

        //Assert
        UpdateStatisticsArgument expectedArgument = UpdateStatisticsArgument.builder()
                                                                            .totalEntries(5L)
                                                                            .totalGrades(5L)
                                                                            .noLessThanFourPercentage(1.1)
                                                                            .maxRatingEntries(5L)
                                                                            .maxRatingPercentage(1.1)
                                                                            .averageRating(1.1)
                                                                            .aboveFourEntries(5L)
                                                                            .aboveFourPercentage(1.1)
                                                                            .entriesWithoutGrades(5L)
                                                                            .noLessThanFourEntries(5L)
                                                                            .build();
        Mockito.verify(statisticsService).update(expectedArgument);
    }
}
