package wordle.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wordle.model.dto.User;
import wordle.security.JwtTokenProvider;
import wordle.services.rest.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/registration")
@Tag(name = "Регистрация", description = "REST контроллер формы регистрации")
public class RegistrationController extends TemplateController {
    private static final String USER_NOT_REGISTERED = "User not registered";
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserService userService, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "Кнопка зарегистрироваться", description = "Кнопка зарегистрироваться", tags = {"Регистрация"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Сущность пользователя", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Ошибка", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Такой пользователь уже существует", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PutMapping(value = "register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.getByEmail(user.getEmail()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User registeredUser = userService.saveUser(user);
            if (registeredUser != null) {
                return createStringOkResponseEntity(jwtTokenProvider.createToken(registeredUser.getEmail(), registeredUser.getRole().name()));
            } else {
                return createResponseEntityWithHttpStatus(USER_NOT_REGISTERED, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return createResponseEntityWithHttpStatus("User with this email is already exist's", HttpStatus.BAD_REQUEST);
        }
    }

}