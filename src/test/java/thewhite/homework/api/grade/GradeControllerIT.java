package thewhite.homework.api.grade;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.reactive.server.WebTestClient;
import thewhite.homework.api.grade.dto.CreateGradeDto;
import thewhite.homework.api.grade.dto.GradeDto;
import thewhite.homework.exception.MessageError;
import thewhite.homework.model.Entry;
import thewhite.homework.model.Grade;
import thewhite.homework.repository.EntryRepository;
import thewhite.homework.repository.GradeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
@EnablePostgresIntegrationTest
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GradeControllerIT {

    @Autowired
    WebTestClient client;

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/grade/create.json")
    @ExpectedDataSet(value = "datasets/api/grade/expected_create.json")
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
                                      .contentType(MediaType.APPLICATION_JSON)
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
                                        .entryId(1L)
                                        .rating(5)
                                        .build();

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .ignoringFields("id")
                  .isEqualTo(expectedBody);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/grade/delete.json")
    @ExpectedDataSet(value = "datasets/api/grade/expected_delete.json")
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
