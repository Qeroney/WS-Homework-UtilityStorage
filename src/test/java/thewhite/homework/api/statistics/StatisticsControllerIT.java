package thewhite.homework.api.statistics;

import com.github.database.rider.core.api.dataset.DataSet;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import thewhite.homework.api.statistics.dto.StatisticsDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
@EnablePostgresIntegrationTest
@FieldDefaults(level = AccessLevel.PRIVATE)
class StatisticsControllerIT {

    @Autowired
    WebTestClient client;

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/files/statistics/info.json")
    void statistics() {
        //Act & Arrange
        StatisticsDto responseBody = client.get()
                                           .uri("/statistics/info")
                                           .exchange()
                                           .expectStatus()
                                           .isOk()
                                           .expectBody(StatisticsDto.class)
                                           .returnResult()
                                           .getResponseBody();

        //Assert
        StatisticsDto expectedBody = StatisticsDto.builder()
                                                  .id(1L)
                                                  .totalEntries(5L)
                                                  .totalGrades(5L)
                                                  .noLessThanFourPercentage(1.1)
                                                  .maxRatingEntries(5L)
                                                  .maxRatingPercentage(1.1)
                                                  .averageRating(5.0)
                                                  .aboveFourEntries(5L)
                                                  .aboveFourPercentage(1.1)
                                                  .entriesWithoutGrades(5L)
                                                  .noLessThanFourEntries(5L)
                                                  .build();

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedBody);
    }
}
