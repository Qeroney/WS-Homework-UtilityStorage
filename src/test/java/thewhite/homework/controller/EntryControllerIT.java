package thewhite.homework.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import thewhite.homework.controller.dto.CreateEntryDto;
import thewhite.homework.controller.dto.EntryDto;
import thewhite.homework.controller.dto.UpdateEntryDto;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
@ExtendWith(SoftAssertionsExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class EntryControllerIT {

    @Autowired
    WebTestClient client;

    @Test
    void create() {
        //Arrange
        EntryDto entry = EntryDto.builder()
                                 .name("name")
                                 .description("desc")
                                 .link("link")
                                 .build();

        CreateEntryDto dto = new CreateEntryDto(entry.getName(), entry.getDescription(), entry.getLink());

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
                                        .link("link")
                                        .build();

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedBody);
    }

    @Test
    void update() {
        //Arrange
        Long id = 1L;
        EntryDto entry = EntryDto.builder()
                                 .id(id)
                                 .name("name")
                                 .description("desc")
                                 .link("link")
                                 .build();

        UpdateEntryDto dto = new UpdateEntryDto(entry.getName(), entry.getDescription(), entry.getLink());

        //Act
        EntryDto responseBody = client.put()
                                      .uri("entry/update/" + id)
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
                                        .link("link")
                                        .build();

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedBody);
    }

    @Test
    void delete() {
        //Arrange
        Long id = 1L;

        //Act
        client.delete()
              .uri("entry/delete/" + id)
              .exchange()
              //Assert
              .expectStatus()
              .isOk();
    }
}
