package wordle.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import wordle.services.rest.AttemptService;
import wordle.services.rest.GameService;
import wordle.model.exceptions.GameException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static wordle.services.rest.GameService.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/game")
@Tag(name = "Игра", description = "REST контроллер игры")
public class GameController implements TemplateController {

    private static final String GAME_STATUS = "gameStatus";
    private static final String CHECK_WORD_STATUS = "checkWordStatus";
    private static final String WORD_CHARACTERS = "wordCharacters";

    private final GameService gameService;
    private final AttemptService attemptService;

    @Autowired
    public GameController(GameService gameService, AttemptService attemptService) {
        this.gameService = gameService;
        this.attemptService = attemptService;
    }

    @Operation(summary = "Возвращает результат шага", description = "Проверит переданное слово и вернет результат проверки", tags = {"Игра"})
    @GetMapping(value = "/stepResult")
    public ResponseEntity<?> getStepResult(
            @Parameter(description = "введенное слово") @RequestParam(value = "inputWord") String inputWord,
            @Parameter(description = "текущий шаг") @RequestParam(value = "step") int step
    ) {
        Map<String, Object> jsonResponse = new HashMap<>();
        try {
            jsonResponse.put(CHECK_WORD_STATUS, gameService.getCheckWordStatus(inputWord));
            jsonResponse.put(WORD_CHARACTERS, gameService.getWordIndices(inputWord));
            jsonResponse.put(GAME_STATUS, gameService.getGameStatus(inputWord, step));
        } catch (GameException gameException) {
            return createErrorResponseEntity(gameException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return createOkResponseEntity(jsonResponse);
    }


    @Operation(summary = "Начнет новую игру", description = "Начнет игру с новым случайным словом", tags = {"Игра"})
    @PostMapping(value = "newGame")
    public ResponseEntity<?> startNewGame() {
        Map<String, Object> jsonResponse = new HashMap<>();
        try {
            gameService.restartGame();
            jsonResponse.put(GAME_STATUS, IN_GAME);
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
            return createOkResponseEntity(Collections.singletonMap(GAME_STATUS, GAME_LOSE));
        } catch (GameException gameException) {
            return createErrorResponseEntity(gameException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Сохранит результат игры", description = "Сохранит результат игры и начислит монеты в случае победы", tags = {"Игра"})
    @PostMapping(value = "endGame")
    @PreAuthorize(("hasAuthority('user') or hasAuthority('admin') "))
    public ResponseEntity<?> endGame(@Parameter(description = "список проверок каждого шага") @RequestBody List<List<wordle.model.dto.WordCharacter>> wordCharacters,
                                     @Parameter(description = "почта пользователя") @RequestParam String email,
                                     @Parameter(description = "кол-во выигранных монет") @RequestParam Integer coins
    ) {
        attemptService.save(wordCharacters, email, coins);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
