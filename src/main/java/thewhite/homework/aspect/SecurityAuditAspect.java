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
import thewhite.homework.model.Grade;
import thewhite.homework.service.securityAudit.SecurityAuditService;
import thewhite.homework.service.securityAudit.argument.CreateSecurityAuditArgument;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SecurityAuditAspect {

    SecurityAuditService securityAuditService;

    @Pointcut("@annotation(thewhite.homework.aspect.annotation.LogCreateGrade)")
    private void controllerPointcut() {}

    @AfterReturning(value = "controllerPointcut()", returning = "grade")
    public void logRequest(Grade grade) {
        CreateSecurityAuditArgument argument = buildRequest(grade);
        securityAuditService.create(argument);
    }

    private CreateSecurityAuditArgument buildRequest(Grade grade) {
        return CreateSecurityAuditArgument.builder()
                                          .gradeId(grade.getId())
                                          .createdAt(LocalDateTime.now())
                                          .info(getRequestInfo())
                                          .build();
    }

    private String getRequestInfo() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            return "ipAddress= " + request.getRemoteAddr() + ", " + "userAgent= " + request.getHeader("User-Agent");
        } else {
            return "ipAddress= , userAgent= ";
        }
    }
}