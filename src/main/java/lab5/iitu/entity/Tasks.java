package lab5.iitu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "tasks")
public class Tasks {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(name = "dueDate", nullable = false)
    private LocalDate dueDate;

    @Column(name = "status")
    private String status;

    @Column(name = "priority")
    private Integer priority;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Categories category;
    @Column(name = "photoPath")
    private String photoPath;
}
