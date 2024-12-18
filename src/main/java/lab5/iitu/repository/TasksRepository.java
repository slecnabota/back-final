package lab5.iitu.repository;

import lab5.iitu.entity.Tasks;
import lab5.iitu.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TasksRepository extends JpaRepository<Tasks, Long> {
    Page<Tasks> findByUser(Users user, Pageable pageable);
}