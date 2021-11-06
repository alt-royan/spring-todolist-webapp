package todolist.springtodolist.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import todolist.springtodolist.api.entity.User;
import todolist.springtodolist.api.exception.TaskNotFoundException;
import todolist.springtodolist.api.exception.UserAlreadyExistException;
import todolist.springtodolist.api.exception.UserNotFoundException;
import todolist.springtodolist.api.model.UserModel;
import todolist.springtodolist.api.repository.UserRepository;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //Register new user
    public UserModel saveUser(User user) throws UserAlreadyExistException {
        if (userRepository.findByLogin(user.getLogin())!=null){
            throw new UserAlreadyExistException(user.getLogin());
        }
        return UserModel.modelOf(userRepository.save(user));
    }

    //Get list of all users
    public Stream<UserModel> getAll(){
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).map(UserModel::modelOf);
    }

    //Get one user by id
    public UserModel getUserById(Integer id) throws UserNotFoundException {
        return userRepository.findById(id).map(UserModel::modelOf).orElseThrow(()->new UserNotFoundException(id));
    }

    //Delete user by id
    public Integer deleteUser(Integer id) throws UserNotFoundException {
        try {
            userRepository.deleteById(id);
            return id;
        }catch(EmptyResultDataAccessException ex){
            throw new UserNotFoundException(id);
        }
    }

}
