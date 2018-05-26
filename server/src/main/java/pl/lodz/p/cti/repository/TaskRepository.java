package pl.lodz.p.cti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.cti.models.TaskModel;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskModel, Long> {

    List<TaskModel> findByAuthorId(Long authorId);
}
