package thewhite.homework.api.securityAudit;

import com.github.database.rider.core.api.dataset.DataSet;
import com.jupiter.tools.spring.test.postgres.annotation.meta.EnablePostgresIntegrationTest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import thewhite.homework.api.PageDto;
import thewhite.homework.api.securityAudit.dto.SearchSecurityAuditDto;
import thewhite.homework.api.securityAudit.dto.SecurityAuditDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebClient
@EnablePostgresIntegrationTest
@FieldDefaults(level = AccessLevel.PRIVATE)
class SecurityAuditControllerIT {

    @Autowired
    WebTestClient client;

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/files/audit/page.json")
    void page() {
        //Arrange
        SearchSecurityAuditDto dto = SearchSecurityAuditDto.builder()
                                                           .info("1")
                                                           .build();

        //Act
        PageDto<SecurityAuditDto> responseBody = client.get()
                                                       .uri(uriBuilder -> uriBuilder.path("/audit/page")
                                                                                    .queryParam("info", dto.getInfo())
                                                                                    .build())
                                                       .exchange()
                                                       .expectStatus()
                                                       .isOk()
                                                       .expectBody(new ParameterizedTypeReference<PageDto<SecurityAuditDto>>() {})
                                                       .returnResult()
                                                       .getResponseBody();

        //Assert
        PageDto<SecurityAuditDto> expectedBody = PageDto.<SecurityAuditDto>builder()
                                                        .totalElements(1L)
                                                        .contents(List.of(SecurityAuditDto.builder()
                                                                                          .id(1L)
                                                                                          .gradeId(UUID.fromString("d1b4e136-647c-4136-88e0-f2a8f19dfb2e"))
                                                                                          .info("ipAddress= 0:0:0:0:0:0:0:1, userAgent= PostmanRuntime/7.35.0")
                                                                                          .createdAt(LocalDateTime.of(2022, 2, 22, 12, 0))
                                                                                          .build()))
                                                        .build();

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedBody);
    }

    @Test
    @DataSet(cleanBefore = true, cleanAfter = true, value = "datasets/api/files/audit/page.json")
    void pageWithoutParams() {
        //Act & Arrange
        PageDto<SecurityAuditDto> responseBody = client.get()
                                                       .uri("/audit/page")
                                                       .exchange()
                                                       .expectStatus()
                                                       .isOk()
                                                       .expectBody(new ParameterizedTypeReference<PageDto<SecurityAuditDto>>() {})
                                                       .returnResult()
                                                       .getResponseBody();

        //Assert
        PageDto<SecurityAuditDto> expectedBody = PageDto.<SecurityAuditDto>builder()
                                                        .totalElements(3L)
                                                        .contents(List.of(SecurityAuditDto.builder()
                                                                                          .id(1L)
                                                                                          .info("ipAddress= 0:0:0:0:0:0:0:1, userAgent= PostmanRuntime/7.35.0")
                                                                                          .createdAt(LocalDateTime.of(2022, 2, 22, 12, 0))
                                                                                          .gradeId(UUID.fromString("d1b4e136-647c-4136-88e0-f2a8f19dfb2e"))
                                                                                          .build(),
                                                                          SecurityAuditDto.builder()
                                                                                          .id(2L)
                                                                                          .info("ipAddress= 0:0:0:0:0:0:0:2, userAgent= PostmanRuntime/7.35.0")
                                                                                          .createdAt(LocalDateTime.of(2022, 3, 23, 13, 0))
                                                                                          .gradeId(UUID.fromString("dbe50c38-66fc-49a0-b99b-b424dde50a6a"))
                                                                                          .build(),
                                                                          SecurityAuditDto.builder()
                                                                                          .id(3L)
                                                                                          .info("ipAddress= 0:0:0:0:0:0:0:3, userAgent= PostmanRuntime/7.35.0")
                                                                                          .createdAt(LocalDateTime.of(2022, 4, 24, 14, 0))
                                                                                          .gradeId(UUID.fromString("f759cfaa-e10a-4799-9e06-11fdd479dee1"))
                                                                                          .build()
                                                                         ))
                                                        .build();

        Assertions.assertThat(responseBody)
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedBody);
    }
}
