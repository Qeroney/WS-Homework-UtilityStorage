package thewhite.homework.service.securityAudit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import thewhite.homework.model.SecurityAudit;
import thewhite.homework.service.securityAudit.argument.CreateSecurityAuditArgument;
import thewhite.homework.service.securityAudit.argument.SearchSecurityAuditArgument;

public interface SecurityAuditService {

    Page<SecurityAudit> page(SearchSecurityAuditArgument argument, Pageable pageable);

    SecurityAudit create(CreateSecurityAuditArgument argument);
}
