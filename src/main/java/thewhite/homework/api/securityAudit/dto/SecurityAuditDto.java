package thewhite.homework.api.securityAudit.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class SecurityAuditDto {

    Long id;

    UUID gradeId;

    String info;

    LocalDateTime createdAt;
}
