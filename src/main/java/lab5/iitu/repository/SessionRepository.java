package lab5.iitu.repository;

import lab5.iitu.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByUsersId(Long userId);
    Optional<Session> findByToken(String token);
}