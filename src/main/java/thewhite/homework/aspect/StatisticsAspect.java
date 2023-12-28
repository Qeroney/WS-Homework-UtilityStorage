package thewhite.homework.aspect;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import thewhite.homework.event.StatisticsEvent;

@Aspect
@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class StatisticsAspect {

    ApplicationEventPublisher publisher;

    @Pointcut("@annotation(thewhite.homework.aspect.annotation.LogStatistics)")
    private void statisticsPointcut() {}

    @After("statisticsPointcut()")
    public void logRequest() {
        publisher.publishEvent(new StatisticsEvent("Updated statistics"));
    }
}


