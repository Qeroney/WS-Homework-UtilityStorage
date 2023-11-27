package thewhite.homework.service.entry.argument;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SearchEntryArgument {

    String description;

    String name;
}
