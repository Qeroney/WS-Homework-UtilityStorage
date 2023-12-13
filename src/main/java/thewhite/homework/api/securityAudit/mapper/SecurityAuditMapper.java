package thewhite.homework.api.securityAudit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import thewhite.homework.api.PageDto;
import thewhite.homework.api.securityAudit.dto.SearchSecurityAuditDto;
import thewhite.homework.api.securityAudit.dto.SecurityAuditDto;
import thewhite.homework.model.SecurityAudit;
import thewhite.homework.service.securityAudit.argument.SearchSecurityAuditArgument;

@Mapper
public interface SecurityAuditMapper {

    SearchSecurityAuditArgument toSearchArgument(SearchSecurityAuditDto dto);

    @Mapping(source = "searchSecurityAudit.content", target = "contents")
    @Mapping(source = "searchSecurityAudit.totalElements", target = "totalElements")
    PageDto<SecurityAuditDto> toSearchResultDto(Page<SecurityAudit> searchSecurityAudit);
}