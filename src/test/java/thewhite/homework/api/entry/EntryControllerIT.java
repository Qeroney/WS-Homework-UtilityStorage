package thewhite.homework.api.entry;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import thewhite.homework.api.PageDto;
import thewhite.homework.api.entry.dto.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@EnablePostgresIntegrationTest
@FieldDefaults(level = AccessLevel.PRIVATE)
class EntryControllerIT {

    @Autowired
    WebTestClient client;

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/files/entry/create.json")
    @ExpectedDataSet(value = "datasets/api/files/entry/expected_create.json")
    void create() {
        //Arrange
        CreateEntryDto dto = CreateEntryDto.builder()
                                           .name("name")
                                           .description("desc")
                                           .links(List.of("link1", "link2"))
                                           .build();

        //Act
        EntryDto responseBody = client.post()
                                      .uri("/entry/create")
                                      .bodyValue(dto)
                                      .exchange()
                                      .expectStatus()
                                      .isOk()
                                      .expectBody(EntryDto.class)
                                      .returnResult()
                                      .getResponseBody();

        //Assert
        EntryDto expectedBody = EntryDto.builder()
                                        .id(1L)
                                        .name("name")
                                        .description("desc")
                                        .links(List.of("link1", "link2"))
                                        .grades(new ArrayList<>())
                                        .build();

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedBody);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/files/entry/update.json")
    @ExpectedDataSet(value = "datasets/api/files/entry/expected_update.json")
    void update() {
        //Arrange
        long id = 1L;
        UpdateEntryDto dto = UpdateEntryDto.builder()
                                           .name("name2")
                                           .description("desc2")
                                           .links(List.of("link3", "link4"))
                                           .build();

        //Act
        EntryDto responseBody = client.put()
                                      .uri("entry/{id}/update", id)
                                      .bodyValue(dto)
                                      .exchange()
                                      .expectStatus()
                                      .isOk()
                                      .expectBody(EntryDto.class)
                                      .returnResult()
                                      .getResponseBody();

        //Assert
        EntryDto expectedBody = EntryDto.builder()
                                        .id(1L)
                                        .name("name2")
                                        .description("desc2")
                                        .links(List.of("link3", "link4"))
                                        .grades(new ArrayList<>())
                                        .build();

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedBody);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/files/entry/delete.json")
    @ExpectedDataSet(value = "datasets/api/files/entry/expected_delete.json")
    void delete() {
        //Arrange
        long id = 1L;

        //Act
        client.delete()
              .uri("entry/{id}/delete", id)
              .exchange()
              //Assert
              .expectStatus()
              .isOk();
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/files/entry/get.json")
    void get() {
        //Arrange
        long id = 1L;

        //Act
        EntryDto responseBody = client.get()
                                      .uri("/entry/{id}/get", id)
                                      .exchange()
                                      .expectStatus()
                                      .isOk()
                                      .expectBody(EntryDto.class)
                                      .returnResult()
                                      .getResponseBody();

        //Assert
        EntryDto expectedBody = EntryDto.builder()
                                        .id(1L)
                                        .name("name")
                                        .description("desc")
                                        .links(List.of("link1", "link2"))
                                        .grades(new ArrayList<>())
                                        .build();

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedBody);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/files/entry/page.json")
    void getPageEntry() {
        // Arrange
        SearchEntryDto dto = SearchEntryDto.builder()
                                           .name("Love")
                                           .description("Homeland")
                                           .build();

        // Act
        PageDto<EntryListDto> responseBody = client.get()
                                                   .uri(uriBuilder -> uriBuilder.path("/entry/page")
                                                                                  .queryParam("name", dto.getName())
                                                                                  .queryParam("description", dto.getDescription())
                                                                                  .build())
                                                   .exchange()
                                                   .expectStatus()
                                                   .isOk()
                                                   .expectBody(new ParameterizedTypeReference<PageDto<EntryListDto>>() {})
                                                   .returnResult()
                                                   .getResponseBody();

        // Assert
        PageDto<EntryListDto> expectedBody = PageDto.<EntryListDto>builder()
                                                        .totalElements(1L)
                                                        .contents(List.of(EntryListDto.builder()
                                                                                     .id(1L)
                                                                                     .name("Love")
                                                                                     .description("Homeland")
                                                                                     .links(List.of("link1", "link2"))
                                                                                     .build()))
                                                        .build();

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedBody);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/files/entry/page.json")
    void getPageEntryWithoutParams() {
        // Act & Arrange
        PageDto<EntryListDto> responseBody = client.get()
                                                     .uri(uriBuilder -> uriBuilder.path("/entry/page")
                                                                                  .build())
                                                     .exchange()
                                                     .expectStatus()
                                                     .isOk()
                                                     .expectBody(new ParameterizedTypeReference<PageDto<EntryListDto>>() {})
                                                     .returnResult()
                                                     .getResponseBody();

        // Assert
        PageDto<EntryListDto> expectedBody = PageDto.<EntryListDto>builder()
                                                        .totalElements(2L)
                                                        .contents(List.of(EntryListDto.builder()
                                                                                     .id(2L)
                                                                                     .name("Clothes")
                                                                                     .description("Wear")
                                                                                     .links(List.of("link3", "link4"))
                                                                                     .build(),
                                                                         EntryListDto.builder()
                                                                                     .id(1L)
                                                                                     .name("Love")
                                                                                     .description("Homeland")
                                                                                     .links(List.of("link1", "link2"))
                                                                                     .build()))
                                                        .build();

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedBody);
    }
}