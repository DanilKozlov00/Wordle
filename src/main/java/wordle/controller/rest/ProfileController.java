package wordle.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wordle.services.rest.AttemptService;
import wordle.services.rest.UserService;
import wordle.services.rest.UserStatisticService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/profile")
@Tag(name = "Профиль", description = "REST контроллер профиля")
@PreAuthorize(("hasAuthority('user') or hasAuthority('admin') "))
@SecurityRequirement(name = "Authorization")
public class ProfileController implements TemplateController {

    private final static String UPDATED = "Updated";
    private final static String NOT_UPDATED = "Not updated";

    private final UserService userService;
    private final AttemptService attemptService;
    private final UserStatisticService userStatisticService;

    @Autowired
    public ProfileController(UserService userService, AttemptService attemptService, UserStatisticService userStatisticService) {
        this.userService = userService;
        this.attemptService = attemptService;
        this.userStatisticService = userStatisticService;
    }

    @Operation(summary = "Вернет попытки пользователя", description = "Вернет попытки пользователя", tags = {"Профиль"})
    @GetMapping("attempts")
    public ResponseEntity<?> getUserAttemptsOrderParam(@Parameter(description = "почта пользователя") @RequestParam String email,
                                                       @Parameter(description = "старт выборки") @RequestParam int start,
                                                       @Parameter(description = "конец выборки") @RequestParam int end,
                                                       @Parameter(description = "параметр выборки") @RequestParam String param,
                                                       @Parameter(description = "направление выборки убывание/возрастание (desc,asc)") String orderBy
    ) {
        return createOkResponseEntity(attemptService.getUserAttemptByUserIdOrderByParam(email, start, end, param, orderBy));
    }

    @Operation(summary = "Вернет количество попыток пользователя", description = "Вернет количество попыток пользователя", tags = {"Профиль"})
    @GetMapping("attemptCount")
    public ResponseEntity<?> getUserAttemptCount(@Parameter(description = "почта пользователя") @RequestParam String email) {
        return createOkResponseEntity(attemptService.getAttemptCountByUserEmail(email));
    }


    @Operation(summary = "Вернет последнюю попытку пользователя", description = "Вернет последнюю попытку пользователя", tags = {"Профиль"})
    @GetMapping("lastAttempt")
    public ResponseEntity<?> getLastUserAttempt(@Parameter(description = "почта пользователя") @RequestParam String email) {
        return createOkResponseEntity(attemptService.getLastAttemptByUserEmail(email));
    }

    @Operation(summary = "Удалит аккаунт пользователя", description = "Удалит аккаунт пользователя", tags = {"Профиль"})
    @PostMapping("deleteAccount")
    public ResponseEntity<?> deleteAccount(@Parameter(description = "почта пользователя") @RequestParam String email) {
        if (userService.deleteUserByEmail(email)) {
            return createOkResponseEntity(HttpStatus.OK);
        }
        return createErrorResponseEntity("Account not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(summary = "Вернет статистику пользователя", description = "Вернет статистику пользователя", tags = {"Профиль"})
    @GetMapping("userStatistic")
    public ResponseEntity<?> getUserStatistic(@Parameter(description = "почта пользователя") @RequestParam String email) {
        return createOkResponseEntity(userStatisticService.getUserStatistic(email));
    }

    @Operation(summary = "Обновит пароль пользователя", description = "Обновит пароль пользователя", tags = {"Профиль"})
    @PostMapping("updatePassword")
    public ResponseEntity<?> updatePassword(@Parameter(description = "почта пользователя") @RequestParam String email,
                                            @Parameter(description = "новый пароль") @RequestParam String newPassword) {
        if (userService.updatePassword(email, newPassword)) {
            createOkResponseEntity(UPDATED);
        }
        return createErrorResponseEntity(NOT_UPDATED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(summary = "Обновит почту пользователя", description = "Обновит почту  пользователя", tags = {"Профиль"})
    @PostMapping("updateEmail")
    public ResponseEntity<?> updateEmail(@Parameter(description = "текущая почта пользователя") @RequestParam String oldEmail,
                                         @Parameter(description = "новая почта пользователя") @RequestParam String newEmail) {
        if (userService.updateEmail(oldEmail, newEmail)) {
            createOkResponseEntity(UPDATED);
        }
        return createErrorResponseEntity(NOT_UPDATED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
