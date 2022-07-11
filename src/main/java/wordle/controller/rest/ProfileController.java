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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wordle.model.dto.User;
import wordle.security.JwtTokenProvider;
import wordle.services.rest.AttemptService;
import wordle.services.rest.UserService;
import wordle.services.rest.UserStatisticService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/profile")
@Tag(name = "Профиль", description = "REST контроллер профиля")
@PreAuthorize(("hasAuthority('user') or hasAuthority('admin') "))
@SecurityRequirement(name = "Authorization")
public class ProfileController extends TemplateController {

    public final static String UPDATED = "Updated";
    public final static String NOT_UPDATED = "Not updated";
    private final static String ERROR = "Error";

    private final UserService userService;
    private final AttemptService attemptService;
    private final UserStatisticService userStatisticService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public ProfileController(UserService userService, AttemptService attemptService, UserStatisticService userStatisticService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.attemptService = attemptService;
        this.userStatisticService = userStatisticService;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Operation(summary = "Вернет сущность пользователя по токену", description = "Вернет сущность пользователя по токену", tags = {"Профиль"})
    @GetMapping("user")
    public ResponseEntity<?> getUserAsToken(@RequestHeader(value = "Authorization") String token) {
        String email = jwtTokenProvider.getUsername(token);
        User user = userService.getByEmail(email);
        if (user != null) {
            return createOkResponseEntity(user);
        }
        return createErrorResponseEntity(ERROR, HttpStatus.BAD_REQUEST);
    }


    @Operation(summary = "Вернет попытки пользователя", description = "Вернет попытки пользователя", tags = {"Профиль"})
    @GetMapping("attempts")
    public ResponseEntity<?> getUserAttemptsOrderParam(@RequestHeader(value = "Authorization") String token,
                                                       @Parameter(description = "старт выборки") @RequestParam int start,
                                                       @Parameter(description = "конец выборки") @RequestParam int end,
                                                       @Parameter(description = "параметр выборки") @RequestParam String param,
                                                       @Parameter(description = "направление выборки убывание/возрастание (desc,asc)") String orderBy
    ) {
        String email = jwtTokenProvider.getUsername(token);
        return createOkResponseEntity(attemptService.getUserAttemptByUserIdOrderByParam(email, start, end, param, orderBy));
    }

    @Operation(summary = "Вернет количество попыток пользователя", description = "Вернет количество попыток пользователя", tags = {"Профиль"})
    @GetMapping("attemptCount")
    public ResponseEntity<?> getUserAttemptCount(@RequestHeader(value = "Authorization") String token) {
        String email = jwtTokenProvider.getUsername(token);
        return createOkResponseEntity(attemptService.getAttemptCountByUserEmail(email));
    }


    @Operation(summary = "Вернет последнюю попытку пользователя", description = "Вернет последнюю попытку пользователя", tags = {"Профиль"})
    @GetMapping("lastAttempt")
    public ResponseEntity<?> getLastUserAttempt(@RequestHeader(value = "Authorization") String token) {
        String email = jwtTokenProvider.getUsername(token);
        return createOkResponseEntity(attemptService.getLastAttemptByUserEmail(email));
    }

    @Operation(summary = "Удалит аккаунт пользователя", description = "Удалит аккаунт пользователя", tags = {"Профиль"})
    @PostMapping("deleteAccount")
    public ResponseEntity<?> deleteAccount(@RequestHeader(value = "Authorization") String token) {
        String email = jwtTokenProvider.getUsername(token);
        if (userService.deleteUserByEmail(email)) {
            return createOkResponseEntity(HttpStatus.OK);
        }
        return createErrorResponseEntity(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(summary = "Вернет статистику пользователя", description = "Вернет статистику пользователя", tags = {"Профиль"})
    @GetMapping("userStatistic")
    public ResponseEntity<?> getUserStatistic(@RequestHeader(value = "Authorization") String token) {
        String email = jwtTokenProvider.getUsername(token);
        return createOkResponseEntity(userStatisticService.getUserStatistic(email));
    }

    @Operation(summary = "Обновит пароль пользователя", description = "Обновит пароль пользователя", tags = {"Профиль"})
    @PostMapping("updatePassword")
    public ResponseEntity<?> updatePassword(@RequestHeader(value = "Authorization") String token,
                                            @Parameter(description = "новый пароль") @RequestParam String newPassword) {
        String email = jwtTokenProvider.getUsername(token);
        if (userService.updatePassword(email, newPassword)) {
            createOkResponseEntity(UPDATED);
        }
        return createErrorResponseEntity(NOT_UPDATED, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(summary = "Обновит почту пользователя", description = "Обновит почту  пользователя", tags = {"Профиль"})
    @PostMapping("updateEmail")
    public ResponseEntity<?> updateEmail(@RequestHeader(value = "Authorization") String token,
                                         @Parameter(description = "новая почта пользователя") @RequestParam String newEmail) {
        String oldEmail = jwtTokenProvider.getUsername(token);
        if (userService.updateEmail(oldEmail, newEmail)) {
            createOkResponseEntity(UPDATED);
        }
        return createErrorResponseEntity(NOT_UPDATED, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PostMapping("update")
    public ResponseEntity<?> updateUser(@RequestHeader(value = "Authorization") String token,
                                        @Parameter(description = "никнейм") @RequestParam(required = false) String nickname,
                                        @Parameter(description = "имя") @RequestParam(required = false) String name,
                                        @Parameter(description = "телефон") @RequestParam(required = false) String phone
    ) {
        String email = jwtTokenProvider.getUsername(token);
        User user = userService.update(email, nickname, name, phone);
        if (user != null) {
            return createOkResponseEntity(user);
        }
        return createErrorResponseEntity(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
