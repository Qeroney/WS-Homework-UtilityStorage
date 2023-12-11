package thewhite.homework.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SecurityAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "securityAudit_id")
    Long id;

    @Column(nullable = false)
    UUID gradeId;

    @Column(nullable = false)
    String info;

    @CreationTimestamp
    @Column(nullable = false)
    LocalDateTime createdAt;
}