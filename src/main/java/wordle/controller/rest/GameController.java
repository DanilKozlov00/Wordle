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
import org.springframework.web.bind.annotation.*;


import wordle.model.dictionary.WordCharacter;
import wordle.services.rest.AttemptService;
import wordle.services.rest.GameService;
import wordle.model.exceptions.GameException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static wordle.view.WordleInterface.GAME_LOSE;
import static wordle.view.WordleInterface.HIDDEN_WORD;
import static wordle.view.WordleInterface.IN_GAME;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/game")
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

    @Operation(summary = "Возвращает результат шага", description = "Проверит переданное слово и вернет некоторый результат", tags = {"Игра"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Проверка проведена",
                    content = @Content(
                            examples = {@ExampleObject(name = "Стандартный ответ", summary = "слово корректно, статус - в игре",
                                    value = "{\n" +
                                            "    \"wordCharacters\": [\n" +
                                            "        {\n" +
                                            "            \"character\": \"w\",\n" +
                                            "            \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "            \"character\": \"o\",\n" +
                                            "            \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "            \"character\": \"r\",\n" +
                                            "            \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "            \"character\": \"d\",\n" +
                                            "            \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                            "        },\n" +
                                            "        {\n" +
                                            "            \"character\": \"s\",\n" +
                                            "            \"characterPosition\": \"INCORRECT_POSITION\"\n" +
                                            "        }\n" +
                                            "    ],\n" +
                                            "    \"gameStatus\": \"In game\",\n" +
                                            "    \"checkWordStatus\": \"Word is correct\"\n" +
                                            "}"
                            ),
                                    @ExampleObject(name = "Стандартный ответ2", summary = "слово корректно, статус - игра проиграна",
                                            value = "{\n" +
                                                    "    \"wordCharacters\": [\n" +
                                                    "        {\n" +
                                                    "            \"character\": \"w\",\n" +
                                                    "            \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                                    "        },\n" +
                                                    "        {\n" +
                                                    "            \"character\": \"o\",\n" +
                                                    "            \"characterPosition\": \"INCORRECT_POSITION\"\n" +
                                                    "        },\n" +
                                                    "        {\n" +
                                                    "            \"character\": \"r\",\n" +
                                                    "            \"characterPosition\": \"INCORRECT_POSITION\"\n" +
                                                    "        },\n" +
                                                    "        {\n" +
                                                    "            \"character\": \"d\",\n" +
                                                    "            \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                                    "        },\n" +
                                                    "        {\n" +
                                                    "            \"character\": \"s\",\n" +
                                                    "            \"characterPosition\": \"MISSING_IN_WORD\"\n" +
                                                    "        }\n" +
                                                    "    ],\n" +
                                                    "    \"gameStatus\": \"Game is lose\",\n" +
                                                    "    \"checkWordStatus\": \"Word is correct\"\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(name = "Стандартный ответ3", summary = "слово некорректно, статус - в игре",
                                            value = "{\n" +
                                                    "    \"wordCharacters\": [],\n" +
                                                    "    \"gameStatus\": \"In game\",\n" +
                                                    "    \"checkWordStatus\": \"Incorrect word length\"\n" +
                                                    "}"
                                    )
                            },
                            mediaType = "application/json",
                            schema = @Schema(implementation = WordCharacter.class))
            ),
            @ApiResponse(responseCode = "500", description = "Ошибка словаря",
                    content = @Content(examples = {
                            @ExampleObject(name = "Ошибка", summary = "Ошибка работы со словарем",
                                    value = "{\n" +
                                            "    \"Error\": \"Dictionary file not found\"\n" +
                                            "}"
                            )
                    }, mediaType = "application/json"))
    })
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Игра началась",
                    content = @Content(examples = {
                            @ExampleObject(name = "Стандартный ответ", summary = "игра запущена, статус - в игре",
                                    value = "{\n" +
                                            "    \"Game status\": \"In game\",\n" +
                                            "    \"Hidden word\": \"spoke\"\n" +
                                            "}"
                            )}, mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Ошибка словаря",
                    content = @Content(examples = {
                            @ExampleObject(name = "Ошибка", summary = "Ошибка запуска игры",
                                    value = "{\n" +
                                            "    \"Error\": \"Dictionary file not found\"\n" +
                                            "}"
                            )
                    }, mediaType = "application/json"))})
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Игра завершена",
                    content = @Content(examples = {
                            @ExampleObject(name = "Стандартный ответ", summary = "игра завершена, статус - игра проиграна",
                                    value = "{\n" +
                                            "    \"Game status\": \"Game is lose\",\n" +
                                            "}"
                            )}, mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Ошибка словаря",
                    content = @Content(examples = {
                            @ExampleObject(name = "Ошибка", summary = "Ошибка запуска игры",
                                    value = "{\n" +
                                            "    \"Error\": \"Dictionary file not found\"\n" +
                                            "}"
                            )
                    }, mediaType = "application/json"))})
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Результат сохранён")})
    @PostMapping(value = "endGame")
    public ResponseEntity<?> endGame(@Parameter(description = "список проверок каждого шага") @RequestBody List<List<wordle.model.dto.WordCharacter>> wordCharacters,
                                     @Parameter(description = "почта пользователя") @RequestParam String email,
                                     @Parameter(description = "кол-во выигранных монет") @RequestParam Integer coins
    ) {
        attemptService.save(wordCharacters, email, coins);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
