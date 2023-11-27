package thewhite.homework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JsonIgnore // сделал костыль из за рекурсии
    Entry entry;

    @JoinColumn(name = "entry_id", nullable = false)
    Long entryId;

    @Column(nullable = false)
    String comment;

    @Column(nullable = false)
    Integer rating;
}
