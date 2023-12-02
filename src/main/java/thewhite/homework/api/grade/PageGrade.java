package thewhite.homework.api.grade;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PageGrade<T> {

    List<T> grades;

    long totalElements;
}
