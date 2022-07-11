package wordle.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import wordle.model.game.GameStatus;
import wordle.model.game.StepResult;
import wordle.security.JwtTokenProvider;
import wordle.services.rest.GameService;
import wordle.model.exceptions.GameException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static wordle.services.rest.GameService.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/game")
@Tag(name = "Игра", description = "REST контроллер игры")
public class GameController extends TemplateController {

    private static final String GAME_STATUS = "gameStatus";
    private final GameService gameService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Возвращает результат шага", description = "Проверит переданное слово и вернет результат проверки", tags = {"Игра"})
    @GetMapping(value = "/stepResult")
    public ResponseEntity<?> getStepResult(
            @Parameter(description = "введенное слово") @RequestParam(value = "inputWord") String inputWord,
            @Parameter(description = "текущий шаг") @RequestParam(value = "step") int step
    ) {
        StepResult stepResult;
        try {
            stepResult = new StepResult(gameService.getCheckWordStatus(inputWord), gameService.getWordIndices(inputWord), gameService.getGameStatus(inputWord, step));
        } catch (GameException gameException) {
            return createErrorResponseEntity(gameException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return createOkResponseEntity(stepResult);
    }


    @Operation(summary = "Начнет новую игру", description = "Начнет игру с новым случайным словом", tags = {"Игра"})
    @PostMapping(value = "newGame")
    public ResponseEntity<?> startNewGame() {
        Map<String, Object> jsonResponse = new HashMap<>();
        try {
            gameService.restartGame();
            jsonResponse.put(GAME_STATUS, GameStatus.IN_GAME);
            jsonResponse.put(HIDDEN_WORD, gameService.getHiddenWord());
        } catch (GameException gameException) {
            return createErrorResponseEntity(gameException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return createOkResponseEntity(jsonResponse);
    }

    @Operation(summary = "Закончит игру", description = "закончит игру", tags = {"Игра"})
    @PostMapping(value = "surrender")
    public ResponseEntity<?> surrender() {
        try {
            gameService.restartGame();
            return createOkResponseEntity(GameStatus.IN_GAME);
        } catch (GameException gameException) {
            return createErrorResponseEntity(gameException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Сохранит результат игры", description = "Сохранит результат игры и начислит монеты в случае победы", tags = {"Игра"})
    @PostMapping(value = "endGame")
    @PreAuthorize(("hasAuthority('user') or hasAuthority('admin') "))
    public ResponseEntity<?> endGame(@Parameter(description = "список проверок каждого шага") @RequestBody List<List<wordle.model.dto.WordCharacter>> wordCharacters,
                                     @Parameter(description = "кол-во выигранных монет") @RequestParam Integer coins,
                                     @RequestHeader(value = "token") String token
    ) {
        String email = jwtTokenProvider.getUsername(token);
        if (gameService.saveGameResult(wordCharacters, email, coins)) {
            return createOkResponseEntity("Saved");
        }
        return createErrorResponseEntity("Not saved", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
