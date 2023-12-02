package thewhite.homework.service.entry.argument;

import lombok.*;

@Value
@Builder
public class SearchEntryArgument {

    String description;

    String name;
}
