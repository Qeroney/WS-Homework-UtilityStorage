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
import org.testcontainers.shaded.com.google.common.collect.Lists;
import thewhite.homework.api.entry.dto.CreateEntryDto;
import thewhite.homework.api.entry.dto.EntryDto;
import thewhite.homework.api.entry.dto.SearchEntryDto;
import thewhite.homework.api.entry.dto.UpdateEntryDto;

import java.util.ArrayList;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@EnablePostgresIntegrationTest
@FieldDefaults(level = AccessLevel.PRIVATE)
class EntryControllerIT {

    @Autowired
    WebTestClient client;

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/entry/create.json")
    @ExpectedDataSet(value = "datasets/api/entry/expected_create.json")
    void create() {
        //Arrange
        CreateEntryDto dto = CreateEntryDto.builder()
                                           .name("name")
                                           .description("desc")
                                           .links(Lists.newArrayList("http://link1.com", "http://link2.com"))
                                           .grades(new ArrayList<>())
                                           .build();

        //Act
        EntryDto responseBody = client.post()
                                      .uri("/entry/create")
                                      .contentType(APPLICATION_JSON)
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
                                        .links(Lists.newArrayList("http://link1.com", "http://link2.com"))
                                        .grades(new ArrayList<>())
                                        .build();

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedBody);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/entry/update.json")
    @ExpectedDataSet(value = "datasets/api/entry/expected_update.json")
    void update() {
        //Arrange
        long id = 1L;
        UpdateEntryDto dto = UpdateEntryDto.builder()
                                           .name("name")
                                           .description("desc")
                                           .links(Lists.newArrayList("http://link1.com", "http://link2.com"))
                                           .build();

        //Act
        EntryDto responseBody = client.put()
                                      .uri("entry/" + id + "/update")
                                      .contentType(APPLICATION_JSON)
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
                                        .links(Lists.newArrayList("http://link1.com", "http://link2.com"))
                                        .build();

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedBody);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/entry/delete.json")
    @ExpectedDataSet(value = "datasets/api/entry/expected_delete.json")
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
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/entry/create.json")
    @ExpectedDataSet(value = "datasets/api/entry/expected_create.json")
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
                                        .links(Lists.newArrayList("http://link1.com", "http://link2.com"))
                                        .build();

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedBody);
    }

    @Test
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
