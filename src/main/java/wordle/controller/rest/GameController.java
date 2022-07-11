package wordle.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import wordle.model.game.StepResult;
import wordle.security.JwtAuthenticationException;
import wordle.security.JwtTokenProvider;
import wordle.services.rest.GameService;
import wordle.model.exceptions.GameException;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/game")
@Tag(name = "Игра", description = "REST контроллер игры")
public class GameController extends TemplateController {
    private final GameService gameService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Возвращает результат шага", description = "Проверит переданное слово и вернет результат проверки", tags = {"Игра"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Результат проверки слова", content = @Content(schema = @Schema(implementation = StepResult.class))),
            @ApiResponse(responseCode = "500", description = "Ошибка при проверке слова", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @GetMapping(value = "/stepResult")
    public ResponseEntity<?> getStepResult(
            @Parameter(description = "введенное слово") @RequestParam(value = "inputWord") String inputWord,
            @Parameter(description = "текущий шаг") @RequestParam(value = "step") int step
    ) {
        StepResult stepResult;
        try {
            stepResult = new StepResult(gameService.getCheckWordStatus(inputWord), gameService.getWordIndices(inputWord), gameService.getGameStatus(inputWord, step));
        } catch (GameException gameException) {
            return createResponseEntityWithHttpStatus(gameException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return createObjectOkResponseEntity(stepResult);
    }


    @Operation(summary = "Начнет новую игру", description = "Начнет игру с новым случайным словом", tags = {"Игра"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Игра начата", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Ошибка начала игры", content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping(value = "newGame")
    public ResponseEntity<?> startNewGame() {
        try {
            gameService.restartGame();
            return createStringOkResponseEntity(gameService.getHiddenWord());
        } catch (GameException gameException) {
            return createResponseEntityWithHttpStatus(gameException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Сохранит результат игры", description = "Сохранит результат игры и начислит монеты в случае победы", tags = {"Игра"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Сохранение успешно", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Сохранение не удалось", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "403", description = "Неверный токен", content = @Content(schema = @Schema(implementation = JwtAuthenticationException.class))),
    })
    @PostMapping(value = "endGame")
    @PreAuthorize(("hasAuthority('user') or hasAuthority('admin') "))
    public ResponseEntity<?> endGame(@Parameter(description = "список проверок каждого шага") @RequestBody List<List<wordle.model.dto.WordCharacter>> wordCharacters,
                                     @Parameter(description = "кол-во выигранных монет") @RequestParam Integer coins,
                                     @RequestHeader(value = "Authorization") String token
    ) {
        String email = jwtTokenProvider.getUsername(token);
        if (gameService.saveGameResult(wordCharacters, email, coins)) {
            return createStringOkResponseEntity("Saved");
        }
        return createResponseEntityWithHttpStatus("Not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
