package thewhite.homework.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Entry {

    private int id;
    private String name;
    private String description;
    private String link;
}
