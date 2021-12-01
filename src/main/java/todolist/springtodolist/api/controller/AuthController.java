package todolist.springtodolist.api.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import todolist.springtodolist.api.dto.AuthResponseDTO;
import todolist.springtodolist.api.dto.StringResponseDTO;
import todolist.springtodolist.api.entity.User;
import todolist.springtodolist.api.exception.UserAlreadyExistException;
import todolist.springtodolist.api.exception.UserNotFoundException;
import todolist.springtodolist.api.exception.WrongPasswordException;
import todolist.springtodolist.api.dto.AuthRequestDTO;
import todolist.springtodolist.api.dto.RegistrationRequestDTO;
import todolist.springtodolist.api.security.JwtProvider;
import todolist.springtodolist.api.service.UserService;

import javax.validation.Valid;

@Api(tags = "User", description = "Authentication and registration")
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @ApiOperation(value = "Registration", notes = "Register new user")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StringResponseDTO> registerUser(@RequestBody @Valid @ApiParam(name="Registration request", value="Required login and password") RegistrationRequestDTO registrationRequest) throws UserAlreadyExistException {
        User user = new User();
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        userService.saveNewDefaultUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new StringResponseDTO("User " + registrationRequest.getLogin() + " was registered", HttpStatus.CREATED));
    }


    @ApiOperation(value = "Authentication", notes = "Authenticate user")
    @PostMapping("/auth")
    public ResponseEntity<AuthResponseDTO> auth(@RequestBody @ApiParam(name="Authentication request", value="Required login and password") @Valid AuthRequestDTO request) throws UserNotFoundException, WrongPasswordException {
        User user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        String token = jwtProvider.generateToken(user);
        return ResponseEntity.ok(new AuthResponseDTO(token, jwtProvider));
    }

}
