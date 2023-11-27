package thewhite.homework.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entry_seq")
    @SequenceGenerator(name = "entry_seq", sequenceName = "entry_id_seq", allocationSize = 1)
    @Column(name = "entry_id")
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String description;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "entry_links", joinColumns = @JoinColumn(name = "entry_id"))
    @Column(name = "link")
    List<String> links;

    @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Grade> grades;
}
