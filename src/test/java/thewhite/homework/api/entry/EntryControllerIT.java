package thewhite.homework.api.entry;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;;
import org.springframework.test.web.reactive.server.WebTestClient;
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
        EntryListDto dto = EntryListDto.builder()
                                       .id(1L)
                                       .name("name")
                                       .description("desc")
                                       .links(List.of("link1", "link2"))
                                       .build();

        // Act
        client.get()
              .uri("/entry/page")
              .exchange()
              // Assert
              .expectStatus().isOk()
              .expectBody()
              .jsonPath("$.entries[0].name").isEqualTo(dto.getName())
              .jsonPath("$.entries[0].description").isEqualTo(dto.getDescription())
              .jsonPath("$.entries[0].id").isEqualTo(dto.getId())
              .jsonPath("$.entries[0].links").value(Matchers.containsInAnyOrder(dto.getLinks().toArray()));
    }
}