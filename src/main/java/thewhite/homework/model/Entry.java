package thewhite.homework.model;

import lombok.*;

@Setter
@Getter
@Builder
public class Entry {

    Long id;
    String name;
    String description;
    String link;
}
