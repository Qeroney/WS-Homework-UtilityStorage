package thewhite.homework.model;

import javax.persistence.*;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Grade {
    @Id
    @GeneratedValue
    @Column(name = "grade_id")
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    Entry entry;

    @Column(nullable = false)
    String comment;

    @Column(nullable = false)
    Integer rating;
}
