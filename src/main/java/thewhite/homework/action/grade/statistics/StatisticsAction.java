package thewhite.homework.action.grade.statistics;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import thewhite.homework.event.StatisticsEvent;
import thewhite.homework.service.entry.EntryService;
import thewhite.homework.service.grade.GradeService;
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
        statisticsService.update(UpdateStatisticsArgument.builder()
                                                         .totalEntries(entryService.getTotalEntries())
                                                         .totalGrades(gradeService.getTotalGrades())
                                                         .averageRating(gradeService.getAverageRating())
                                                         .aboveFourEntries(entryService.getAboveFourEntries())
                                                         .aboveFourPercentage(entryService.percentageAboveFourEntries())
                                                         .entriesWithoutGrades(entryService.getEntriesWithoutGrades())
                                                         .maxRatingEntries(entryService.entriesWithAverageGradeEqualsFive())
                                                         .maxRatingPercentage(entryService.percentageEntriesWithAverageGradeEqualsFive())
                                                         .noLessThanFourEntries(entryService.getNoLessThanFourEntries())
                                                         .noLessThanFourPercentage(entryService.percentageNoLessThanFourEntries())
                                                         .build());
    }
}