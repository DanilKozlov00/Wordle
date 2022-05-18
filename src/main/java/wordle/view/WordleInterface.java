package wordle.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wordle.controller.CharacterPosition;
import wordle.controller.GameWordle;
import wordle.controller.validators.WordleRule;
import wordle.utils.exceptions.GameException;

import java.util.AbstractMap;
import java.util.List;
import java.util.Scanner;

/**
 * Класс отвечает за взаимодействие игры с пользователем
 */
@Component
public class WordleInterface {

    private final String INCORRECT_WORD;
    private final String INCORRECT_WORD_LENGTH;
    private final String INCORRECT_INPUT;
    private final String GAME_WIN;
    private final String GAME_LOSE;
    private final String START;
    private final String EXIT;
    private final String INPUT_WORD;
    private final String HIDDEN_WORD;
    private final String END_GAME_MESSAGE;
    private final String EMPTY_STRING;
    private final String SPACE_STRING;

    private final GameWordle gameWordle;

    @Autowired
    public WordleInterface(GameWordle gameWordle,
                           @Value("${WordleInterface.INCORRECT_WORD}") String INCORRECT_WORD,
                           @Value("${WordleInterface.INCORRECT_WORD_LENGTH}") String INCORRECT_WORD_LENGTH,
                           @Value("${WordleInterface.INCORRECT_INPUT}") String INCORRECT_INPUT,
                           @Value("${WordleInterface.GAME_WIN}") String GAME_WIN,
                           @Value("${WordleInterface.GAME_LOSE}") String GAME_LOSE,
                           @Value("${WordleInterface.START}") String START,
                           @Value("${WordleInterface.EXIT}") String EXIT,
                           @Value("${WordleInterface.INPUT_WORD}") String INPUT_WORD,
                           @Value("${WordleInterface.HIDDEN_WORD}") String HIDDEN_WORD,
                           @Value("${WordleInterface.END_GAME_MESSAGE}") String END_GAME_MESSAGE,
                           @Value("${WordleInterface.EMPTY_STRING}") String EMPTY_STRING,
                           @Value("${WordleInterface.SPACE_STRING}") String SPACE_STRING) {
        this.gameWordle = gameWordle;
        this.INCORRECT_WORD = INCORRECT_WORD;
        this.INCORRECT_WORD_LENGTH = INCORRECT_WORD_LENGTH;
        this.INCORRECT_INPUT = INCORRECT_INPUT;
        this.GAME_WIN = GAME_WIN;
        this.GAME_LOSE = GAME_LOSE;
        this.START = START;
        this.EXIT = EXIT;
        this.INPUT_WORD = INPUT_WORD;
        this.HIDDEN_WORD = HIDDEN_WORD;
        this.END_GAME_MESSAGE = END_GAME_MESSAGE;
        this.EMPTY_STRING = EMPTY_STRING;
        this.SPACE_STRING = SPACE_STRING;
    }

    /**
     * Метод старта игры. Отвечает за общение игры с пользователем
     */
    public void startGame() {

        try {
            gameWordle.startGame();
        } catch (GameException gameException) {
            gameException.printStackTrace();
            return;
        }

        Scanner scanner = new Scanner(System.in);
        String stepResult = EMPTY_STRING, inputWord;
        System.out.println(CharactersDisplay.getCharactersNotation());


        do {
            while (!stepResult.equals(GAME_LOSE) && !stepResult.equals(GAME_WIN)) {
                System.out.println(INPUT_WORD);
                inputWord = scanner.nextLine();
                stepResult = inputWordStepResult(inputWord);
                try {
                    if (!stepResult.equals(INCORRECT_WORD_LENGTH) && !stepResult.equals(INCORRECT_INPUT)) {
                        printCharactersPositions(gameWordle.getGameRule().checkCharactersPosition(inputWord, gameWordle.getHiddenWord()));
                    }
                } catch (GameException gameException) {
                    System.err.println(gameException.getMessage());
                }
                System.out.println(stepResult);
                if (stepResult.equals(GAME_LOSE)) {
                    System.out.println(HIDDEN_WORD + gameWordle.getHiddenWord());
                }
            }
            System.out.println(END_GAME_MESSAGE);
            inputWord = scanner.nextLine();
            if (inputWord.equals(START)) {
                try {
                    gameWordle.restartGame();
                } catch (GameException gameException) {
                    gameException.printStackTrace();
                    return;
                }
                stepResult = EMPTY_STRING;
            }
        } while (!inputWord.equals(EXIT));
    }

    private String inputWordStepResult(String inputWord) {
        WordleRule gameRule = gameWordle.getGameRule();
        try {
            if (gameRule.isCorrectWord(inputWord)) {
                if (gameRule.isInvalidStep(gameWordle.getCountSteps())) {
                    return GAME_LOSE;
                }
                if (!inputWord.equals(gameWordle.getHiddenWord())) {
                    gameWordle.incrementCountSteps();
                    return INCORRECT_WORD;
                }
                return GAME_WIN;
            } else {
                if (inputWord.length() != gameRule.getWORD_LENGTH()) {
                    return INCORRECT_WORD_LENGTH;
                }
                return INCORRECT_INPUT;
            }
        } catch (GameException gameException) {
            System.err.println(gameException.getMessage());
            return GAME_LOSE;
        }
    }

    private void printCharactersPositions(List<AbstractMap.SimpleEntry<Character, CharacterPosition>> charactersPositionsPairs) {
        StringBuilder positions = new StringBuilder();
        for (AbstractMap.SimpleEntry<Character, CharacterPosition> pair : charactersPositionsPairs) {
            System.out.print(pair.getKey() + SPACE_STRING);
            positions.append(CharactersDisplay.getCharacterPositionDisplay(pair.getValue())).append(SPACE_STRING);
        }
        System.out.println();
        System.out.println(positions);
    }
}
