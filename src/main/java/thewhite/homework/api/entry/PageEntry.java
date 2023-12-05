package thewhite.homework.api.entry;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
@Builder
public class PageEntry<T> {

    List<T> entries;

    long totalElements;
}
