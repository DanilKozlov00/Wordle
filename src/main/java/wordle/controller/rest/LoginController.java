package wordle.controller.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wordle.model.dto.User;
import wordle.services.rest.UserService;
import wordle.model.exceptions.UserException;

@RestController
@RequestMapping(value = "/login")
@Tag(name = "Вход", description = "REST контроллер формы входа")
public class LoginController implements TemplateController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Кнопка войти", description = "Кнопка войти", tags = {"Вход"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход",
                    content = @Content(examples = {
                            @ExampleObject(name = "Стандартный ответ", summary = "Успешный вход, вернет сущность пользователя",
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
            @ApiResponse(responseCode = "403", description = "Некорректный пароль",
                    content = @Content(examples = {
                            @ExampleObject(name = "Ошибка", summary = "Некорректный пароль",
                                    value = "{\n" +
                                            "    \"Status\": \"Incorrect password\"\n" +
                                            "}"
                            )
                    }, mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Несуществующий email",
                    content = @Content(examples = {
                            @ExampleObject(name = "Ошибка", summary = "Несуществующий email",
                                    value = "{\n" +
                                            "    \"Status\": \"User not found\"\n" +
                                            "}"
                            )
                    }, mediaType = "application/json")),
    })
    @PostMapping("signIn")
    public ResponseEntity<?> getUserByEmail(@Parameter(required = true, description = "email пользователя") @RequestParam String email, @Parameter(required = true, description = "пароль пользователя") @RequestParam String password) {
        try {
            return createOkResponseEntity(userService.authorizeUser(email, password));
        } catch (UserException userException) {
            return createErrorResponseEntity(userException.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

}
