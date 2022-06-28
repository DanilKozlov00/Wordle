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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wordle.model.dto.Attempt;
import wordle.services.rest.AttemptService;
import wordle.services.rest.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/profile")
@Tag(name = "Профиль", description = "REST контроллер профиля")
public class ProfileController implements TemplateController {

    private final UserService userService;
    private final AttemptService attemptService;

    @Autowired
    public ProfileController(UserService userService, AttemptService attemptService) {
        this.userService = userService;
        this.attemptService = attemptService;
    }

    @Operation(summary = "Вернет попытки пользователя", description = "Вернет попытки пользователя", tags = {"Профиль"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Попытки пользователя",
                    content = @Content(examples = {
                            @ExampleObject(name = "Стандартный ответ", summary = "одна попытка",
                                    value = "[\n" +
                                            "    {\n" +
                                            "        \"date\": [\n" +
                                            "            2022,\n" +
                                            "            6,\n" +
                                            "            22\n" +
                                            "        ],\n" +
                                            "        \"coins_win\": 0,\n" +
                                            "        \"is_admin_accrued\": false,\n" +
                                            "        \"is_win\": false,\n" +
                                            "        \"steps\": [\n" +
                                            "            {\n" +
                                            "                \"number\": 0,\n" +
                                            "                \"wordCharacters\": [\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"Г\",\n" +
                                            "                        \"characterPosition\": \"INCORRECT_POSITION\"\n" +
                                            "                    },\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"А\",\n" +
                                            "                        \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                            "                    },\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"Р\",\n" +
                                            "                        \"characterPosition\": \"CORRECT_POSITION\"\n" +
                                            "                    },\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"А\",\n" +
                                            "                        \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                            "                    },\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"Ж\",\n" +
                                            "                        \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                            "                    }\n" +
                                            "                ]\n" +
                                            "            },\n" +
                                            "            {\n" +
                                            "                \"number\": 1,\n" +
                                            "                \"wordCharacters\": [\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"W\",\n" +
                                            "                        \"characterPosition\": \"INCORRECT_POSITION\"\n" +
                                            "                    },\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"O\",\n" +
                                            "                        \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                            "                    },\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"R\",\n" +
                                            "                        \"characterPosition\": \"CORRECT_POSITION\"\n" +
                                            "                    },\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"D\",\n" +
                                            "                        \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                            "                    },\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"S\",\n" +
                                            "                        \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                            "                    }\n" +
                                            "                ]\n" +
                                            "            }\n" +
                                            "        ]\n" +
                                            "    }\n" +
                                            "]"
                            )}, mediaType = "application/json", schema = @Schema(implementation = Attempt.class)))})
    @GetMapping("attempts")
    public ResponseEntity<?> getUserAttempts(@Parameter(description = "почта пользователя") @RequestParam String email,
                                             @Parameter(description = "старт выборки") @RequestParam int start,
                                             @Parameter(description = "конец выборки") @RequestParam int end) {
        return createOkResponseEntity(attemptService.getAllByUserEmail(email, start, end));
    }

    @Operation(summary = "Вернет количество попыток пользователя", description = "Вернет количество попыток пользователя", tags = {"Профиль"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Количество попыток")})
    @GetMapping("attemptCount")
    public ResponseEntity<?> getUserAttemptCount(@Parameter(description = "почта пользователя") @RequestParam String email) {
        return createOkResponseEntity(attemptService.getAttemptCountByUserEmail(email));
    }


    @Operation(summary = "Вернет последнюю попытку пользователя", description = "Вернет последнюю попытку пользователя", tags = {"Профиль"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Попытка пользователя",
                    content = @Content(examples = {
                            @ExampleObject(name = "Стандартный ответ", summary = "одна попытка",
                                    value = "[\n" +
                                            "    {\n" +
                                            "        \"date\": [\n" +
                                            "            2022,\n" +
                                            "            6,\n" +
                                            "            22\n" +
                                            "        ],\n" +
                                            "        \"coins_win\": 0,\n" +
                                            "        \"is_admin_accrued\": false,\n" +
                                            "        \"is_win\": false,\n" +
                                            "        \"steps\": [\n" +
                                            "            {\n" +
                                            "                \"number\": 0,\n" +
                                            "                \"wordCharacters\": [\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"Г\",\n" +
                                            "                        \"characterPosition\": \"INCORRECT_POSITION\"\n" +
                                            "                    },\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"А\",\n" +
                                            "                        \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                            "                    },\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"Р\",\n" +
                                            "                        \"characterPosition\": \"CORRECT_POSITION\"\n" +
                                            "                    },\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"А\",\n" +
                                            "                        \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                            "                    },\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"Ж\",\n" +
                                            "                        \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                            "                    }\n" +
                                            "                ]\n" +
                                            "            },\n" +
                                            "            {\n" +
                                            "                \"number\": 1,\n" +
                                            "                \"wordCharacters\": [\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"W\",\n" +
                                            "                        \"characterPosition\": \"INCORRECT_POSITION\"\n" +
                                            "                    },\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"O\",\n" +
                                            "                        \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                            "                    },\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"R\",\n" +
                                            "                        \"characterPosition\": \"CORRECT_POSITION\"\n" +
                                            "                    },\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"D\",\n" +
                                            "                        \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                            "                    },\n" +
                                            "                    {\n" +
                                            "                        \"character\": \"S\",\n" +
                                            "                        \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                            "                    }\n" +
                                            "                ]\n" +
                                            "            }\n" +
                                            "        ]\n" +
                                            "    }\n" +
                                            "]"
                            )}, mediaType = "application/json"))})
    @GetMapping("lastAttempt")
    public ResponseEntity<?> getLastUserAttempt(@Parameter(description = "почта пользователя") @RequestParam String email) {
        return createOkResponseEntity(attemptService.getLastAttemptByUserEmail(email));
    }

    @Operation(summary = "Удалит аккаунт пользователя", description = "Удалит аккаунт пользователя", tags = {"Профиль"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Удалит аккаунт пользователя"),
            @ApiResponse(responseCode = "500", description = "Ошибка удаления аккаунта")
    })
    @PostMapping("/deleteAccount")
    public ResponseEntity<?> deleteAccount(@Parameter(description = "почта пользователя") @RequestParam String email) {
        if (userService.deleteUserByEmail(email)) {
            return createOkResponseEntity(HttpStatus.OK);
        }
        return createErrorResponseEntity("Account not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
