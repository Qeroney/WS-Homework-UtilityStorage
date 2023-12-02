package thewhite.homework.api.entry.dto;

import lombok.*;

@Value
@Builder
public class SearchEntryDto {

    String description;

    String name;
}
