package todolist.springtodolist.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import todolist.springtodolist.api.entity.Role;
import todolist.springtodolist.api.entity.Status;
import todolist.springtodolist.api.entity.User;
import todolist.springtodolist.api.exception.UserAlreadyExistException;
import todolist.springtodolist.api.exception.UserNotFoundException;
import todolist.springtodolist.api.exception.WrongPasswordException;
import todolist.springtodolist.api.repository.UserRepository;

import javax.transaction.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Save new user with unique login
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

    //Get list of all users
    public Iterable<User> getAll(){
        return userRepository.findAll();
    }

    //Get one user by id
    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
    }

    //Delete user by id
    public Long deleteUser(Long id) throws UserNotFoundException {
        try {
            userRepository.deleteById(id);
            return id;
        }catch(EmptyResultDataAccessException ex){
            throw new UserNotFoundException(id);
        }
    }


    //Find user by login and check password
    public User findByLoginAndPassword(String login, String password) throws UserNotFoundException, WrongPasswordException {
        User user = findByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }else{
                throw new WrongPasswordException();
            }
        }else{
            throw new UserNotFoundException(login);
        }
    }

}
