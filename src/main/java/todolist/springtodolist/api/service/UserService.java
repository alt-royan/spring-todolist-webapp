package todolist.springtodolist.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todolist.springtodolist.api.entity.User;
import todolist.springtodolist.api.exception.UserAlreadyExistException;
import todolist.springtodolist.api.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) throws UserAlreadyExistException {
        if (userRepository.findByLogin(user.getLogin())!=null){
            throw new UserAlreadyExistException("User with this login already exist");
        }
        return userRepository.save(user);
    }
}
