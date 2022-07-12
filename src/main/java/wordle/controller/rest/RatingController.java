package wordle.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wordle.model.dto.Attempt;
import wordle.model.dto.User;
import wordle.services.rest.UserService;
import wordle.services.rest.UserStatisticService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/rating")
@Tag(name = "Рейтинг", description = "REST контроллер формы рейтинга")
public class RatingController extends TemplateController {

    private final UserStatisticService userStatisticService;
    private final UserService userService;

    @Autowired
    public RatingController(UserStatisticService userStatisticService, UserService userService) {
        this.userStatisticService = userStatisticService;
        this.userService = userService;
    }

    @Operation(summary = "Получить список игроков по рейтингу", description = "Получить список игроков по рейтингу монет", tags = {"Рейтинг"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Попытки игроков", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Attempt.class))))
    })
    @GetMapping("users")
    public ResponseEntity<?> getUsersRating(
            @Parameter(description = "старт выборки") @RequestParam int start,
            @Parameter(description = "конец выборки") @RequestParam int end,
            @Parameter(description = "параметр выборки") @RequestParam String param,
            @Parameter(description = "направление выборки убывание/возрастание (desc,asc)") String orderBy
    ) {
        return createObjectOkResponseEntity(userStatisticService.getUsersStatistic(start, end, param, orderBy));
    }

    @Operation(summary = "Получить кол-во игроков в рейтинге", description = "Получить кол-во игроков в рейтинге", tags = {"Рейтинг"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Количество попыток игроков", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("statisticsCount")
    public ResponseEntity<?> getStatisticsCount() {
        return createStringOkResponseEntity(userStatisticService.getUsersStatisticsCount());
    }


    @Operation(summary = "Поиск в рейтинге по почте", description = "Поиск в рейтинге по почте", tags = {"Рейтинг"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Сущность пользователя", content = @Content(schema = @Schema(implementation = User.class)))
    })
    @GetMapping("userByEmail")
    public ResponseEntity<?> getUserByEmail(
            @Parameter(description = "email") @RequestParam String email
    ) {
        return createObjectOkResponseEntity(userService.getByEmail(email));
    }

    @Operation(summary = "Поиск в рейтинге по имени", description = "Поиск в рейтинге по имени", tags = {"Рейтинг"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Сущность пользователя", content = @Content(schema = @Schema(implementation = User.class)))
    })
    @GetMapping("userByName")
    public ResponseEntity<?> getUserByName(
            @Parameter(description = "name") @RequestParam String name
    ) {
        return createObjectOkResponseEntity(userService.getByName(name));
    }

    @Operation(summary = "Поиск в рейтинге по имени и почте", description = "Поиск в рейтинге по имени и почте", tags = {"Рейтинг"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Сущность пользователя", content = @Content(schema = @Schema(implementation = User.class)))
    })
    @GetMapping("userByNameAndEmail")
    public ResponseEntity<?> getUserByNameAndEmail(
            @Parameter(description = "name") @RequestParam String name,
            @Parameter(description = "email") @RequestParam String email
    ) {
        return createObjectOkResponseEntity(userService.getByNameAndEmail(name, email));
    }

    @Operation(summary = "Поиск позиции в рейтинге", description = "Поиск позиции в рейтинге", tags = {"Рейтинг"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Позиция пользователя в рейтинге", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("userPosition")
    public ResponseEntity<?> getUserPosition(
            @Parameter(description = "email") @RequestParam String email
    ) {
        return createStringOkResponseEntity(userService.getUserRatingPosition(email));
    }
}
