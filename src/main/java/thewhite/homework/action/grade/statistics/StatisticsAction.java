package thewhite.homework.action.grade.statistics;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import thewhite.homework.event.StatisticsEvent;
import thewhite.homework.service.entry.EntryService;
import thewhite.homework.service.entry.EntryStatistics;
import thewhite.homework.service.grade.GradeService;
import thewhite.homework.service.grade.GradeStatistics;
import thewhite.homework.service.statistics.StatisticsService;
import thewhite.homework.service.statistics.argument.UpdateStatisticsArgument;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StatisticsAction {

    EntryService entryService;

    GradeService gradeService;

    StatisticsService statisticsService;

    @Async
    @EventListener
    public void statisticsListener(StatisticsEvent event) {
        EntryStatistics entryStatistics = entryService.getEntryStatistics();
        GradeStatistics gradeStatistics = gradeService.getGradeStatistics();
        statisticsService.update(UpdateStatisticsArgument.builder()
                                                         .totalEntries(entryStatistics.getTotalEntries())
                                                         .totalGrades(gradeStatistics.getTotalGrades())
                                                         .averageRating(gradeStatistics.getAverageRating())
                                                         .aboveFourEntries(entryStatistics.getAboveFourEntries())
                                                         .aboveFourPercentage(entryStatistics.getAboveFourPercentage())
                                                         .entriesWithoutGrades(entryStatistics.getEntriesWithoutGrades())
                                                         .maxRatingEntries(entryStatistics.getMaxRatingEntries())
                                                         .maxRatingPercentage(entryStatistics.getMaxRatingPercentage())
                                                         .noLessThanFourEntries(entryStatistics.getNoLessThanFourEntries())
                                                         .noLessThanFourPercentage(entryStatistics.getNoLessThanFourPercentage())
                                                         .build());
    }
}