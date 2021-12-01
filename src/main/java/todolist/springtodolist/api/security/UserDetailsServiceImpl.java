package todolist.springtodolist.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import todolist.springtodolist.api.entity.User;
import todolist.springtodolist.api.exception.UserNotFoundException;
import todolist.springtodolist.api.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        try {
            User user = userService.findByLogin(login);
            return SecureUser.fromUserEntity(user);
        }catch (UserNotFoundException e){
            throw new UsernameNotFoundException("Could not find user");
        }
    }


}
