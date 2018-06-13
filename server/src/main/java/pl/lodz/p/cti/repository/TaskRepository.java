package pl.lodz.p.cti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.cti.models.TaskModel;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskModel, Long> {

    List<TaskModel> findByAuthorId(Long authorId);

    @Transactional
    @Modifying
    @Query("UPDATE TaskModel t SET t.taskTable = ?1 WHERE t.id = ?2")
    void setPositionTask(int taskTable, long id);
}
