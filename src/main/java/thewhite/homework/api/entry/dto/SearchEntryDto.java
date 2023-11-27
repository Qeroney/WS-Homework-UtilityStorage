package thewhite.homework.api.entry.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SearchEntryDto {

    String description;

    String name;
}
