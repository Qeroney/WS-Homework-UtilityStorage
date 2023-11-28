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
import org.springframework.test.web.reactive.server.WebTestClient;
import thewhite.homework.api.entry.dto.CreateEntryDto;
import thewhite.homework.api.entry.dto.EntryDto;
import thewhite.homework.api.entry.dto.SearchEntryDto;
import thewhite.homework.api.entry.dto.UpdateEntryDto;

import java.util.ArrayList;

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
                                           .links(new ArrayList<>())
                                           .grades(new ArrayList<>())
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
                                        .links(new ArrayList<>())
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
                                           .links(new ArrayList<>())
                                           .grades(new ArrayList<>())
                                           .build();

        //Act
        EntryDto responseBody = client.put()
                                      .uri("entry/" + id + "/update")
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
                                        .links(new ArrayList<>())
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
              .uri("entry/" + id + "/delete")
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
                                      .uri("/entry/" + id + "/get")
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
                                        .links(new ArrayList<>())
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
        SearchEntryDto searchEntryDto = SearchEntryDto.builder()
                                                      .description("desc")
                                                      .name("name")
                                                      .build();

        // Act
        client.get()
              .uri("/entry/page")
              .exchange()
              // Assert
              .expectStatus().isOk()
              .expectBody()
              .jsonPath("$.content[0].name").isEqualTo(searchEntryDto.getName())
              .jsonPath("$.content[0].description").isEqualTo(searchEntryDto.getDescription());
    }
}
