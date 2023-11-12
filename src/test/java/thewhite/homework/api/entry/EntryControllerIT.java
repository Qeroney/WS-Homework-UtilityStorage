package thewhite.homework.api.entry;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.reactive.server.WebTestClient;
import thewhite.homework.api.entry.dto.CreateEntryDto;
import thewhite.homework.api.entry.dto.EntryDto;
import thewhite.homework.api.entry.dto.UpdateEntryDto;
import thewhite.homework.model.Entry;
import thewhite.homework.repository.entry.EntryRepository;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
@ExtendWith(SoftAssertionsExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class EntryControllerIT {

    @Autowired
    WebTestClient client;

    @Autowired
    EntryRepository repository;

    @BeforeEach
    void setUp() {
        Map<Long, Entry> entries = new HashMap<>();
        entries.put(1L, Entry.builder()
                             .id(1L)
                             .name("name")
                             .description("desc")
                             .link("link")
                             .build());
        ReflectionTestUtils.setField(repository, "entries", entries);
    }

    @Test
    void create() {
        //Arrange
        CreateEntryDto dto = CreateEntryDto.builder()
                                           .name("name")
                                           .description("desc")
                                           .link("link")
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
                                        .link("link")
                                        .build();

        Assertions.assertThat(responseBody)
                  .isEqualTo(expectedBody);
    }

    @Test
    void update() {
        //Arrange
        long id = 1L;
        UpdateEntryDto dto = UpdateEntryDto.builder()
                                           .name("name")
                                           .description("desc")
                                           .link("link")
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
                                        .link("link")
                                        .build();

        Assertions.assertThat(responseBody)
                  .isEqualTo(expectedBody);
    }

    @Test
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
                                        .link("link")
                                        .build();

        Assertions.assertThat(responseBody)
                  .isEqualTo(expectedBody);
    }

    @Test
    void findEntryByName() {
        //Arrange
        String name = "name";

        //Act
        client.get()
              .uri(uriBuilder -> uriBuilder.path("/entry/find/" + name)
                                           .build())
              .exchange()
              //Assert
              .expectStatus()
              .isOk()
              .expectBody()
              .jsonPath("$.content[0].name").isEqualTo(name)
              .jsonPath("$.content[0].description").isEqualTo("desc")
              .jsonPath("$.content[0].link").isEqualTo("link");
    }
}
