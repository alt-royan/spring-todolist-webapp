package todolist.springtodolist.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import todolist.springtodolist.api.dto.AuthResponseDTO;
import todolist.springtodolist.api.entity.User;
import todolist.springtodolist.api.exception.UserAlreadyExistException;
import todolist.springtodolist.api.exception.UserNotFoundException;
import todolist.springtodolist.api.exception.WrongPasswordException;
import todolist.springtodolist.api.dto.AuthRequestDTO;
import todolist.springtodolist.api.dto.RegistrationRequestDTO;
import todolist.springtodolist.api.security.JwtProvider;
import todolist.springtodolist.api.service.UserService;

import javax.validation.Valid;


@RestController
@RequestMapping
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid RegistrationRequestDTO registrationRequest) throws UserAlreadyExistException {
        User user = new User();
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        userService.saveNewDefaultUser(user);
        return "User "+registrationRequest.getLogin()+" was registered";
    }

    @PostMapping("/auth")
    public AuthResponseDTO auth(@RequestBody @Valid AuthRequestDTO request) throws UserNotFoundException, WrongPasswordException {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        String token = jwtProvider.generateToken(user);
        return new AuthResponseDTO(token, jwtProvider);
    }

}
