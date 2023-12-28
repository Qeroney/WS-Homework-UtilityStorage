package thewhite.homework.event;

import org.springframework.context.ApplicationEvent;

public class StatisticsEvent extends ApplicationEvent {

    public StatisticsEvent(Object source) {
        super(source);
    }
}
