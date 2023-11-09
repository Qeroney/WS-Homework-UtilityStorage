package thewhite.homework.model;

import lombok.*;

@Value
@Builder
public class Entry {

    Long id;
    String name;
    String description;
    String link;
}
