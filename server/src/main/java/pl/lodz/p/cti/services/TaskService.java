package pl.lodz.p.cti.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.lodz.p.cti.models.TaskModel;
import pl.lodz.p.cti.repository.TaskRepository;

import java.util.List;

@Service @RequiredArgsConstructor public class TaskService {

    private final TaskRepository taskRepository;

    public TaskModel save(TaskModel userModel) {
        return taskRepository.save(userModel);
    }

    public List<TaskModel> findAllTask() {
        return taskRepository.findAll();
    }

    public TaskModel findTask(Long id) {
        return taskRepository.findOne(id);
    }

    public List<TaskModel> getTasksAuthor(Long authorId) {
        return taskRepository.findByAuthorId(authorId);
    }

    public TaskModel updateTask(TaskModel taskModel) {
        return taskRepository.save(taskModel);
    }

    public void deleteTask(Long taskId) {
        TaskModel taskModel = taskRepository.findOne(taskId);
        if (taskModel != null) {
            taskRepository.delete(taskId);
        }
    }
}
