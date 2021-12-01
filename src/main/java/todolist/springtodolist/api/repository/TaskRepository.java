package todolist.springtodolist.api.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import todolist.springtodolist.api.entity.Status;
import todolist.springtodolist.api.entity.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, String> {

    List<Task> findAllByUser_Id(Long id);

    List<Task> findAllByUser_IdAndStatus(Long user_id, Status status);

    void deleteByIdAndUser_Id(String task_id, Long user_id);

    Optional<Task> findByIdAndUser_Id(String task_id, Long user_id);

    Optional<Task> findByIdAndUser_IdAndStatus(String task_id, Long user_id, Status status);

}
