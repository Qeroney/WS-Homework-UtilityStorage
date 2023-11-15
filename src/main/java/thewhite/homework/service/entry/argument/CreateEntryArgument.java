package thewhite.homework.service.entry.argument;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateEntryArgument {
    String name;
    String description;
    String link;
}
