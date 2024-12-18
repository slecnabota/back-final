package lab5.iitu.entity;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    String token;

    @OneToOne
    @JoinColumn(unique = true)
    Users users;
}
