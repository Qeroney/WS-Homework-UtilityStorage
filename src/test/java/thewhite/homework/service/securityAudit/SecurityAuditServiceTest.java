package thewhite.homework.service.securityAudit;

import com.querydsl.core.types.Predicate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import thewhite.homework.model.SecurityAudit;
import thewhite.homework.repository.SecurityAuditRepository;
import thewhite.homework.service.securityAudit.argument.CreateSecurityAuditArgument;
import thewhite.homework.service.securityAudit.argument.SearchSecurityAuditArgument;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class SecurityAuditServiceTest {

    private final SecurityAuditRepository securityAuditRepository = Mockito.mock(SecurityAuditRepository.class);

    private final SecurityAuditService securityAuditService = new SecurityAuditServiceImpl(securityAuditRepository);

    @Test
    void create() {
        //Arrange
        SecurityAudit securityAudit = Mockito.mock(SecurityAudit.class);

        CreateSecurityAuditArgument argument = CreateSecurityAuditArgument.builder()
                                                                          .gradeId(UUID.fromString("d1b4e136-647c-4136-88e0-f2a8f19dfb2e"))
                                                                          .info("ipAddress= 0:0:0:0:0:0:0:3, userAgent= PostmanRuntime/7.35.0")
                                                                          .createdAt(LocalDateTime.of(2022, 4, 24, 14, 0))
                                                                          .build();
        Mockito.when(securityAuditRepository.save(any())).thenReturn(securityAudit);

        //Act
        SecurityAudit actual = securityAuditService.create(argument);

        //Assert
        ArgumentCaptor<SecurityAudit> securityAuditArgumentCaptor = ArgumentCaptor.forClass(SecurityAudit.class);

        Mockito.verify(securityAuditRepository).save(securityAuditArgumentCaptor.capture());

        SecurityAudit expectedAudit = SecurityAudit.builder()
                                                   .info("ipAddress= 0:0:0:0:0:0:0:3, userAgent= PostmanRuntime/7.35.0")
                                                   .gradeId(UUID.fromString("d1b4e136-647c-4136-88e0-f2a8f19dfb2e"))
                                                   .createdAt(LocalDateTime.of(2022, 4, 24, 14, 0))
                                                   .build();
        Assertions.assertThat(actual).isEqualTo(securityAudit);
        Assertions.assertThat(securityAuditArgumentCaptor.getValue())
                  .usingRecursiveComparison()
                  .withStrictTypeChecking()
                  .isEqualTo(expectedAudit);
    }

    @Test
    void getAuditPage() {
        //Arrange
        SearchSecurityAuditArgument argument = SearchSecurityAuditArgument.builder()
                                                                          .info("ipAddress= 0:0:0:0:0:0:0:3, userAgent= PostmanRuntime/7.35.0")
                                                                          .build();
        Pageable pageable = PageRequest.of(0, 10);
        PageImpl<SecurityAudit> securityAudits = new PageImpl<>(Collections.singletonList(new SecurityAudit()));
        Mockito.when(securityAuditRepository.findAll(any(Predicate.class), eq(pageable))).thenReturn(securityAudits);

        //Act
        Page<SecurityAudit> auditPage = securityAuditService.getAuditPage(argument, pageable);

        //Assert
        ArgumentCaptor<Predicate> predicateArgumentCaptor = ArgumentCaptor.forClass(Predicate.class);

        Mockito.verify(securityAuditRepository).findAll(predicateArgumentCaptor.capture(), eq(pageable));

        Assertions.assertThat(auditPage).isEqualTo(securityAudits);
        Assertions.assertThat(predicateArgumentCaptor.getValue().toString())
                  .isEqualTo("containsIc(securityAudit.info,ipAddress= 0:0:0:0:0:0:0:3, userAgent= PostmanRuntime/7.35.0)");
    }
}
