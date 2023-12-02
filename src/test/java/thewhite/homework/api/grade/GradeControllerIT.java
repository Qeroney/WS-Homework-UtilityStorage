package thewhite.homework.api.grade;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import thewhite.homework.api.grade.dto.CreateGradeDto;
import thewhite.homework.api.grade.dto.GradeDto;
import thewhite.homework.exception.MessageError;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
@EnablePostgresIntegrationTest
@FieldDefaults(level = AccessLevel.PRIVATE)
class GradeControllerIT {

    @Autowired
    WebTestClient client;

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/files/grade/create.json")
    @ExpectedDataSet(value = "datasets/api/files/grade/expected_create.json")
    void create() {
        //Arrange
        CreateGradeDto dto = CreateGradeDto.builder()
                                           .comment("com")
                                           .entryId(1L)
                                           .rating(5)
                                           .build();

        //Act
        GradeDto responseBody = client.post()
                                      .uri("grade/create")
                                      .bodyValue(dto)
                                      .exchange()
                                      .expectStatus()
                                      .isOk()
                                      .expectBody(GradeDto.class)
                                      .returnResult()
                                      .getResponseBody();

        //Assert
        GradeDto expectedBody = GradeDto.builder()
                                        .comment("com")
                                        .rating(5)
                                        .build();

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .ignoringFields("id")
                  .isEqualTo(expectedBody);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/files/grade/delete.json")
    @ExpectedDataSet(value = "datasets/api/files/grade/expected_delete.json")
    void delete() {
        //Arrange
        UUID id = UUID.fromString("d1b4e136-647c-4136-88e0-f2a8f19dfb2e");

        //Act
        client.delete()
              .uri("grade/" + id + "/delete")
              .exchange()
              //Assert
              .expectStatus()
              .isOk();
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/files/grade/page.json")
    void getGradePage() {
        //Arrange
        long id = 1L;
        GradeDto dto = GradeDto.builder()
                               .id(UUID.fromString("dbe50c38-66fc-49a0-b99b-b424dde50a6a"))
                               .comment("com2")
                               .rating(3)
                               .build();

        //Act
        client.get()
              .uri("/grade/page/" + id)
              .exchange()
              //Assert
              .expectStatus().isOk()
              .expectBody()
              .jsonPath("$.grades[0].rating").isEqualTo(dto.getRating())
              .jsonPath("$.grades[0].comment").isEqualTo(dto.getComment())
              .jsonPath("$.grades[0].id").value(equalTo(dto.getId().toString()));
    }

    @Test
    void testRatingValidation() {
        // Arrange
        CreateGradeDto dto = CreateGradeDto.builder()
                                           .comment("com")
                                           .entryId(1L)
                                           .rating(6)
                                           .build();

        // Act
        MessageError responseBody = client.post()
                                          .uri("grade/create")
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .bodyValue(dto)
                                          .exchange()
                                          .expectStatus()
                                          .isBadRequest()
                                          .expectBody(MessageError.class)
                                          .returnResult()
                                          .getResponseBody();

        //Assert
        MessageError error = MessageError.of("оценка не должна быть больше 5");

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(error);
    }

    @Test
    void testCommentValidation() {
        // Arrange
        CreateGradeDto dto = CreateGradeDto.builder()
                                           .comment("")
                                           .entryId(1L)
                                           .rating(4)
                                           .build();

        // Act
        MessageError responseBody = client.post()
                                          .uri("grade/create")
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .bodyValue(dto)
                                          .exchange()
                                          .expectStatus()
                                          .isBadRequest()
                                          .expectBody(MessageError.class)
                                          .returnResult()
                                          .getResponseBody();

        //Assert
        MessageError error = MessageError.of("не указан комментарий");

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(error);
    }

    @Test
    void testEntryIdValidation() {
        // Arrange
        CreateGradeDto dto = CreateGradeDto.builder()
                                           .comment("com")
                                           .entryId(2L)
                                           .rating(5)
                                           .build();

        // Act
        MessageError responseBody = client.post()
                                          .uri("grade/create")
                                          .contentType(MediaType.APPLICATION_JSON)
                                          .bodyValue(dto)
                                          .exchange()
                                          .expectStatus()
                                          .is4xxClientError()
                                          .expectBody(MessageError.class)
                                          .returnResult()
                                          .getResponseBody();

        //Assert
        MessageError error = MessageError.of("Запись не найдена");

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(error);
    }
}
