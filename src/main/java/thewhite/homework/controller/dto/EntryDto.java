package thewhite.homework.controller.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@Builder
public class EntryDto {

    Long id;
    String name;
    String description;
    String link;
}
