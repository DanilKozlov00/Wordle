package wordle.view;

import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String INCORRECT_WORD = "Incorrect word";
    private static final String INCORRECT_WORD_LENGTH = "Incorrect word length";
    private static final String INCORRECT_INPUT = "This word is not contains in dictionary";
    private static final String GAME_WIN = "Game is win";
    private static final String GAME_LOSE = "Game is lose";
    private static final String START = "start";
    private static final String EXIT = "exit";
    private static final String INPUT_WORD = "Enter word: ";
    private static final String HIDDEN_WORD = "Hidden word: ";
    private static final String END_GAME_MESSAGE = "Print 'start' to restart game or 'exit' to exit";
    private static final String EMPTY_STRING = "";
    private static final String SPACE_STRING = " ";

    private GameWordle gameWordle;

    @Autowired
    public WordleInterface(GameWordle gameWordle) {
        this.gameWordle = gameWordle;
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
                if (inputWord.length() != WordleRule.WORD_LENGTH) {
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
