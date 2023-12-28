package thewhite.homework.api.statistics;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thewhite.homework.api.statistics.dto.StatisticsDto;
import thewhite.homework.api.statistics.mapper.StatisticsMapper;
import thewhite.homework.model.Statistics;
import thewhite.homework.service.statistics.StatisticsService;

@RequiredArgsConstructor
@RestController
@RequestMapping("statistics")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Tag(name = "Контроллер для работы со статистикой")
public class StatisticsController {

    StatisticsService statisticsService;

    StatisticsMapper statisticsMapper;

    @GetMapping("info")
    @Operation(description = "Получение статистики")
    public StatisticsDto statistics() {
        Statistics statistics = statisticsService.getStatistics();
        return statisticsMapper.toDto(statistics);
    }
}
