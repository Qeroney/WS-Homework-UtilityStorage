package thewhite.homework.controller.dto;

import lombok.*;

@Value
@Builder
public class EntryDto {

    Long id;
    String name;
    String description;
    String link;
}
