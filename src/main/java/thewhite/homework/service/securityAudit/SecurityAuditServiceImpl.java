package thewhite.homework.service.securityAudit;

import lombok.AccessLevel;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import thewhite.homework.model.QSecurityAudit;
import thewhite.homework.model.SecurityAudit;
import thewhite.homework.repository.SecurityAuditRepository;
import thewhite.homework.service.securityAudit.argument.CreateSecurityAuditArgument;
import thewhite.homework.service.securityAudit.argument.SearchSecurityAuditArgument;
import thewhite.homework.utils.QPredicates;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SecurityAuditServiceImpl implements SecurityAuditService {

    SecurityAuditRepository securityAuditRepository;

    QSecurityAudit qSecurityAudit = QSecurityAudit.securityAudit;

    @Override
    @Transactional(readOnly = true)
    public Page<SecurityAudit> page(SearchSecurityAuditArgument argument, Pageable pageable) {
        Predicate predicate = buildPredicate(argument);

        return securityAuditRepository.findAll(predicate, pageable);
    }

    @Override
    @Transactional
    public SecurityAudit create(CreateSecurityAuditArgument argument) {
        return securityAuditRepository.save(SecurityAudit.builder()
                                                         .gradeId(argument.getGradeId())
                                                         .info(argument.getInfo())
                                                         .createdAt(argument.getCreatedAt())
                                                         .build());
    }

    private Predicate buildPredicate(SearchSecurityAuditArgument argument) {
        return QPredicates.builder()
                          .add(argument.getInfo(), qSecurityAudit.info::containsIgnoreCase)
                          .buildAnd();
    }
}
