package thewhite.homework.service.statistics;

import thewhite.homework.model.Statistics;
import thewhite.homework.service.statistics.argument.UpdateStatisticsArgument;

public interface StatisticsService {

    Statistics update(UpdateStatisticsArgument argument);

    Statistics getStatistics();
}
