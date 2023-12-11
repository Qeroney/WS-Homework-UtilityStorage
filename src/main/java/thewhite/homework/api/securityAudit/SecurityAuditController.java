package thewhite.homework.api.securityAudit;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import thewhite.homework.api.PageDto;
import thewhite.homework.api.securityAudit.dto.SearchSecurityAuditDto;
import thewhite.homework.api.securityAudit.dto.SecurityAuditDto;
import thewhite.homework.api.securityAudit.mapper.SecurityAuditMapper;
import thewhite.homework.model.SecurityAudit;
import thewhite.homework.service.securityAudit.SecurityAuditService;
import thewhite.homework.service.securityAudit.argument.SearchSecurityAuditArgument;

@RequiredArgsConstructor
@RestController
@RequestMapping("audit")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Tag(name = "Контроллер для работы с аудитом")
public class SecurityAuditController {

    SecurityAuditService securityAuditService;

    SecurityAuditMapper securityAuditMapper;

    @GetMapping("page")
    @Operation(description = "получение постраничного списка с поиском по полю info")
    public PageDto<SecurityAuditDto> getSecurityAuditPage(SearchSecurityAuditDto dto, Pageable pageable) {

        SearchSecurityAuditArgument argument = securityAuditMapper.toSearchArgument(dto);
        Page<SecurityAudit> page = securityAuditService.getAuditPage(argument, pageable);
        Page<SecurityAuditDto> audit = page.map(securityAuditMapper::toDto);

        return new PageDto<>(audit.getContent(), page.getTotalElements());
    }
}
