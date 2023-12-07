package thewhite.homework.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class PageDto<T> {

    List<T> contents;

    long totalElements;
}
