package thewhite.homework.controller.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Value
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EntryDto {

    Long id;
    String name;
    String description;
    String link;
}
