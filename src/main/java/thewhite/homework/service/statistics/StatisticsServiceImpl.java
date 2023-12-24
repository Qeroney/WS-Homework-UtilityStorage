package thewhite.homework.service.statistics;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import thewhite.homework.model.Statistics;
import thewhite.homework.repository.StatisticsRepository;
import thewhite.homework.service.statistics.argument.UpdateStatisticsArgument;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StatisticsServiceImpl implements StatisticsService {

    StatisticsRepository statisticsRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Statistics update(UpdateStatisticsArgument argument) {
        Statistics statistics = Optional.ofNullable(statisticsRepository.findFirstByOrderById())
                                        .orElseGet(Statistics::new);

        statistics.setTotalEntries(argument.getTotalEntries());
        statistics.setTotalGrades(argument.getTotalGrades());
        statistics.setAverageRating(argument.getAverageRating());
        statistics.setAboveFourEntries(argument.getAboveFourEntries());
        statistics.setAboveFourPercentage(argument.getAboveFourPercentage());
        statistics.setMaxRatingEntries(argument.getMaxRatingEntries());
        statistics.setMaxRatingPercentage(argument.getMaxRatingPercentage());
        statistics.setNoLessThanFourEntries(argument.getNoLessThanFourEntries());
        statistics.setNoLessThanFourPercentage(argument.getNoLessThanFourPercentage());
        statistics.setEntriesWithoutGrades(argument.getEntriesWithoutGrades());

        return statisticsRepository.save(statistics);
    }

    @Override
    @Transactional(readOnly = true)
    public Statistics getStatistics() {
        return Optional.ofNullable(statisticsRepository.findFirstByOrderById())
                       .orElseGet(Statistics::new);
    }
}