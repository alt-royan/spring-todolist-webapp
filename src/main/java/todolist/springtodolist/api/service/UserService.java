package todolist.springtodolist.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import todolist.springtodolist.api.entity.Role;
import todolist.springtodolist.api.entity.Status;
import todolist.springtodolist.api.entity.Task;
import todolist.springtodolist.api.entity.User;
import todolist.springtodolist.api.exception.TaskNotFoundException;
import todolist.springtodolist.api.exception.UserAlreadyExistException;
import todolist.springtodolist.api.exception.UserNotFoundException;
import todolist.springtodolist.api.exception.WrongPasswordException;
import todolist.springtodolist.api.repository.TaskRepository;
import todolist.springtodolist.api.repository.UserRepository;

import javax.transaction.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**Admin section**/

    //Save new ADMIN
    public User saveNewAdmin(User user) throws UserAlreadyExistException {
        if (userRepository.findByLogin(user.getLogin()).isPresent()){
            throw new UserAlreadyExistException(user.getLogin());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_ADMIN);
        user.setStatus(Status.ACTIVE);
        return userRepository.save(user);
    }

    //Get one user by id
    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
    }

    //Get list of all users
    public Iterable<User> getAll(){
        return userRepository.findAll();
    }

    //Mark user as deleted but not actually delete
    public Long markUserDel(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
        user.setStatus(Status.DELETE);
        userRepository.save(user);
        return id;
    }

    //Actually delete user
    public Long deleteUser(Long id) throws UserNotFoundException {
        try{
            userRepository.deleteById(id);
        }catch(EmptyResultDataAccessException ex){
            throw new UserNotFoundException(id);
        }
        return id;
    }

    //Mark user as active
    public Long activateUser(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
        user.setStatus(Status.ACTIVE);
        userRepository.save(user);
        return id;
    }

    /**User section**/

    //Save new USER
    public User saveNewDefaultUser(User user) throws UserAlreadyExistException {
        if (userRepository.findByLogin(user.getLogin()).isPresent()){
            throw new UserAlreadyExistException(user.getLogin());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setStatus(Status.ACTIVE);
        return userRepository.save(user);
    }

    //Find user by login
    public User findByLogin(String login) throws UserNotFoundException {
        return userRepository.findByLogin(login).orElseThrow(()->new UserNotFoundException(login));
    }


    //Find user by login and check password
    public User findByLoginAndPassword(String login, String password) throws UserNotFoundException, WrongPasswordException {
        User user = findByLogin(login);
        if (user != null) {
            if (user.getStatus()==Status.DELETE){
                throw new UserNotFoundException(login);
            }
            else if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }else{
                throw new WrongPasswordException();
            }
        }else{
            throw new UserNotFoundException(login);
        }
    }

}
