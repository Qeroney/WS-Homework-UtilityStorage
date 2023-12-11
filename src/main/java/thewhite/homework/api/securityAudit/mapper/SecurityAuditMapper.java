package thewhite.homework.api.securityAudit.mapper;

import org.mapstruct.Mapper;
import thewhite.homework.api.securityAudit.dto.SearchSecurityAuditDto;
import thewhite.homework.api.securityAudit.dto.SecurityAuditDto;
import thewhite.homework.model.SecurityAudit;
import thewhite.homework.service.securityAudit.argument.SearchSecurityAuditArgument;

@Mapper
public interface SecurityAuditMapper {

    SecurityAuditDto toDto(SecurityAudit securityAudit);

    SearchSecurityAuditArgument toSearchArgument(SearchSecurityAuditDto dto);
}
