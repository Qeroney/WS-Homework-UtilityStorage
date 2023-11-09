package thewhite.homework.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Entry {

    Long id;
    String name;
    String description;
    String link;
}
