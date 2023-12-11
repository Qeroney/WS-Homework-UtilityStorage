package thewhite.homework.service.securityAudit.argument;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class CreateSecurityAuditArgument {

    UUID gradeId;

    String info;

    LocalDateTime createdAt;
}
