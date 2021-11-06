package todolist.springtodolist.api.repository;

import org.springframework.data.repository.CrudRepository;
import todolist.springtodolist.api.entity.Task;

public interface TaskRepository extends CrudRepository<Task, String> {
    Iterable<Task> findAllByUser_Id(Integer id);
}
