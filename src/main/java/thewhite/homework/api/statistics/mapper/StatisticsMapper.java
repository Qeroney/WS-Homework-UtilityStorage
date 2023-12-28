package thewhite.homework.api.statistics.mapper;

import org.mapstruct.Mapper;
import thewhite.homework.api.statistics.dto.StatisticsDto;
import thewhite.homework.model.Statistics;

@Mapper
public interface StatisticsMapper {

    StatisticsDto toDto(Statistics statistics);
}
