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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import thewhite.homework.api.PageDto;
import thewhite.homework.api.grade.dto.CreateGradeDto;
import thewhite.homework.api.grade.dto.GradeDto;
import thewhite.homework.api.grade.dto.SearchGradeDto;
import thewhite.homework.exception.MessageError;

import java.util.List;
import java.util.UUID;

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
              .uri("grade/{id}/delete", id)
              .exchange()
              //Assert
              .expectStatus()
              .isOk();
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/files/grade/page.json")
    void page() {
        //Arrange
        SearchGradeDto dto = SearchGradeDto.builder()
                                           .rating(5)
                                           .entryId(1L)
                                           .build();

        //Act
        PageDto<GradeDto> responseBody = client.get()
                                               .uri(uriBuilder -> uriBuilder.path("/grade/page")
                                                                            .queryParam("entryId", dto.getEntryId())
                                                                            .queryParam("rating", dto.getRating())
                                                                            .build())
                                               .exchange()
                                               .expectStatus()
                                               .isOk()
                                               .expectBody(new ParameterizedTypeReference<PageDto<GradeDto>>() {})
                                               .returnResult()
                                               .getResponseBody();

        //Assert
        PageDto<GradeDto> expectedBody = PageDto.<GradeDto>builder()
                                                .totalElements(1L)
                                                .contents(List.of(GradeDto.builder()
                                                                          .id(UUID.fromString("d1b4e136-647c-4136-88e0-f2a8f19dfb2e"))
                                                                          .comment("com")
                                                                          .rating(5)
                                                                          .build()))
                                                .build();

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedBody);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/files/grade/page.json")
    void pageWithoutParams() {
        //Arrange
        long entryId = 1L;

        //Act
        PageDto<GradeDto> responseBody = client.get()
                                               .uri(uriBuilder -> uriBuilder.path("/grade/page")
                                                                            .queryParam("entryId", entryId)
                                                                            .build())
                                               .exchange()
                                               .expectStatus()
                                               .isOk()
                                               .expectBody(new ParameterizedTypeReference<PageDto<GradeDto>>() {})
                                               .returnResult()
                                               .getResponseBody();

        //Assert
        PageDto<GradeDto> expectedBody = PageDto.<GradeDto>builder()
                                                .totalElements(2L)
                                                .contents(List.of(GradeDto.builder()
                                                                          .id(UUID.fromString("dbe50c38-66fc-49a0-b99b-b424dde50a6a"))
                                                                          .rating(4)
                                                                          .comment("com2")
                                                                          .build(),
                                                                  GradeDto.builder()
                                                                          .id(UUID.fromString("d1b4e136-647c-4136-88e0-f2a8f19dfb2e"))
                                                                          .comment("com")
                                                                          .rating(5)
                                                                          .build()))
                                                .build();

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedBody);
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
                                           .entryId(5L)
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
