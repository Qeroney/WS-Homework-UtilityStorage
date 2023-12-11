package thewhite.homework.aspect;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import thewhite.homework.api.grade.dto.GradeDto;
import thewhite.homework.service.securityAudit.SecurityAuditService;
import thewhite.homework.service.securityAudit.argument.CreateSecurityAuditArgument;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GradeAspect {

    SecurityAuditService securityAuditService;

    @Pointcut("@annotation(thewhite.homework.aspect.annotation.LogCreateGrade)")
    public void controllerPointcut() {}

    @AfterReturning(value = "controllerPointcut()", returning = "gradeDto")
    public void logRequest(GradeDto gradeDto) {
        CreateSecurityAuditArgument argument = buildRequest(gradeDto);
        securityAuditService.create(argument);
    }

    private CreateSecurityAuditArgument buildRequest(GradeDto dto) {
        return CreateSecurityAuditArgument.builder()
                                          .gradeId(dto.getId())
                                          .createdAt(LocalDateTime.now())
                                          .info(getRequestInfo())
                                          .build();
    }

    private String getRequestInfo() {
        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();

        return "ipAddress= " + request.getRemoteAddr() + ", " + "userAgent= " + request.getHeader("User-Agent");
    }
}