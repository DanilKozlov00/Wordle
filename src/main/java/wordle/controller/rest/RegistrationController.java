package wordle.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wordle.model.dto.User;
import wordle.services.rest.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/registration")
@Tag(name = "Регистрация", description = "REST контроллер формы регистрации")
public class RegistrationController implements TemplateController {

    private static final String USER_NOT_REGISTERED = "User not registered";
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService UserService) {
        this.userService = UserService;
    }

    @Operation(summary = "Кнопка зарегистрироваться", description = "Кнопка зарегистрироваться", tags = {"Регистрация"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная регистрация",
                    content = @Content(examples = {
                            @ExampleObject(name = "Стандартный ответ", summary = "Успешная регистрация, вернет сущность пользователя",
                                    value = "{\n" +
                                            "    \"email\": \"test@gmail.com\",\n" +
                                            "    \"password\": \"pswd\",\n" +
                                            "    \"phone\": \"89066666666\",\n" +
                                            "    \"name\": \"test\",\n" +
                                            "    \"nickname\": null,\n" +
                                            "    \"balance\": 0,\n" +
                                            "    \"role\": \"ROLE_user\"\n" +
                                            "}"
                            )}, schema = @Schema(implementation = User.class), mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Ошибка регистрации",
                    content = @Content(examples = {
                            @ExampleObject(name = "Ошибка", summary = "Ошибка регистрации",
                                    value = "{\n" +
                                            "    \"Status\": \"User not created\"\n" +
                                            "}"
                            )
                    }, mediaType = "application/json")),
    })
    @PutMapping(value = "register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User registeredUser = userService.saveUser(user);
        if (registeredUser != null) {
            return createOkResponseEntity(registeredUser);
        } else {
            return createErrorResponseEntity(USER_NOT_REGISTERED, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}