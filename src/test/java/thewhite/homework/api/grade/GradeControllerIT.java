package thewhite.homework.api.grade;

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
import thewhite.homework.model.Entry;
import thewhite.homework.model.Grade;
import thewhite.homework.repository.entry.EntryRepository;
import thewhite.homework.repository.grade.GradeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
@ExtendWith(SoftAssertionsExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GradeControllerIT {

    @Autowired
    WebTestClient client;

    @Autowired
    GradeRepository gradeRepository;

    @Autowired
    EntryRepository entryRepository;

    @BeforeEach
    void setUp() {
        Map<Long, Entry> entries = new HashMap<>();
        entries.put(1L, Entry.builder()
                             .id(1L)
                             .name("name")
                             .description("desc")
                             .link("link")
                             .build());
        ReflectionTestUtils.setField(entryRepository, "entries", entries);
        Map<UUID, Grade> gradeMap = new HashMap<>();
        gradeMap.put(UUID.fromString("1ed5e3ea-2ab6-4dcd-8c64-a9ef8334bbb0"),
                     Grade.builder()
                          .id(UUID.fromString("1ed5e3ea-2ab6-4dcd-8c64-a9ef8334bbb1"))
                          .entryId(1L)
                          .comment("comment")
                          .rating(3)
                          .build());
        ReflectionTestUtils.setField(gradeRepository, "gradeMap", gradeMap);
    }

    @Test
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
    void delete() {
        //Arrange
        UUID id = UUID.randomUUID();

        //Act
        client.delete()
              .uri("grade/" + id + "/delete")
              .exchange()
              //Assert
              .expectStatus()
              .isOk();
    }

    @Test
    void findEntryById() {
        //Arrange
        long entryId = 1L;

        //Act
        List<GradeDto> responseBody = client.get()
                                            .uri("grade/" + entryId + "/getAll")
                                            .exchange()
                                            .expectStatus()
                                            .isOk()
                                            .expectBodyList(GradeDto.class)
                                            .returnResult()
                                            .getResponseBody();

        //Assert
        List<GradeDto> expectedBody = Lists.newArrayList(GradeDto.builder()
                                                                 .entryId(entryId)
                                                                 .rating(3)
                                                                 .comment("comment")
                                                                 .build());
        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .ignoringFields("id")
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

        // Act & Assert
        client.post()
              .uri("grade/create")
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue(dto)
              .exchange()
              .expectStatus()
              .is5xxServerError();
    }

    @Test
    void testCommentValidation() {
        // Arrange
        CreateGradeDto dto = CreateGradeDto.builder()
                                           .comment("")
                                           .entryId(1L)
                                           .rating(4)
                                           .build();

        // Act & Assert
        client.post()
              .uri("grade/create")
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue(dto)
              .exchange()
              .expectStatus()
              .is5xxServerError();
    }

    @Test
    void testEntryIdValidation() {
        // Arrange
        CreateGradeDto dto = CreateGradeDto.builder()
                                           .comment("com")
                                           .entryId(2L)
                                           .rating(5)
                                           .build();

        // Act & Assert
        client.post()
              .uri("grade/create")
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue(dto)
              .exchange()
              .expectStatus()
              .is4xxClientError();
    }
}
