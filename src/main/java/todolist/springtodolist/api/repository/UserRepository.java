package todolist.springtodolist.api.repository;

import org.springframework.data.repository.CrudRepository;
import todolist.springtodolist.api.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByLogin(String username);
}
