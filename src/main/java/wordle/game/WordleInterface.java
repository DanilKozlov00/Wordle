package wordle.game;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.List;
import java.util.Scanner;

/**
 * Класс отвечает за взаимодействие игры с пользователем
 */
public class WordleInterface {

    private static final String INCORRECT_WORD = "Incorrect word";
    private static final String INCORRECT_WORD_LENGTH = "Incorrect word length";
    private static final String INCORRECT_INPUT = "This word is not contains in dictionary";
    private static final String GAME_WIN = "Game is win";
    private static final String GAME_LOSE = "Game is lose";
    private static final String START = "start";
    private static final String EXIT = "exit";
    private static final String INPUT_WORD = "Введите слово: ";
    private static final String HIDDEN_WORD = "Hidden word: ";
    private static final String END_GAME_MESSAGE = "Print 'start' to restart game or 'exit' to exit";
    private static final String EMPTY_STRING = "";
    private static final String SPACE_STRING = " ";
    private static final String[] INTERFACE_INFO = {"\nThe '", "' character indicates that you picked", " the right letter but it’s in the wrong spot.", " the right letter in the correct spot", " the letter is not included in the word at all."};

    private GameWordle gameWordle;

    public WordleInterface(GameWordle gameWordle) {
        this.gameWordle = gameWordle;
    }

    /**
     * Метод старта игры. Отвечает за общение игры с пользователем
     */
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        String stepResult = EMPTY_STRING, inputWord;
        System.out.println(getInterfaceNotations());
        try {
            do {
                while (!stepResult.equals(GAME_LOSE) && !stepResult.equals(GAME_WIN)) {
                    System.out.println(INPUT_WORD);
                    inputWord = scanner.nextLine();
                    stepResult = inputWordStepResult(inputWord);
                    if (!stepResult.equals(INCORRECT_WORD_LENGTH) && !stepResult.equals(INCORRECT_INPUT)) {
                        printCharactersPositions(gameWordle.getGameRule().checkCharactersPosition(inputWord, gameWordle.getHiddenWord()));
                    }
                    System.out.println(stepResult);
                    if (stepResult.equals(GAME_LOSE)) {
                        System.out.println(HIDDEN_WORD + gameWordle.getHiddenWord());
                    }
                }
                System.out.println(END_GAME_MESSAGE);
                inputWord = scanner.nextLine();
                if (inputWord.equals(START)) {
                    gameWordle.restartGame();
                    stepResult = EMPTY_STRING;
                }
            } while (!inputWord.equals(EXIT));
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Dictionary file not found");
        } catch (IOException ioException) {
            System.out.println("Error while reading dictionary file");
        }
    }

    private String inputWordStepResult(String inputWord) throws IOException {
        WordleRule gameRule = gameWordle.getGameRule();
        if (gameRule.isCorrectWord(inputWord)) {
            if (gameRule.isValidStep(gameWordle.getCountSteps())) {
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
    }

    private void printCharactersPositions(List<AbstractMap.SimpleEntry<Character, CharacterPosition>> charactersPositionsPairs) {
        StringBuilder positions = new StringBuilder();
        for (AbstractMap.SimpleEntry<Character, CharacterPosition> pair : charactersPositionsPairs) {
            System.out.print(pair.getKey() + SPACE_STRING);
            positions.append(pair.getValue().getIndicator()).append(SPACE_STRING);
        }
        System.out.println();
        System.out.println(positions);
    }

    private String getInterfaceNotations() {
        return INTERFACE_INFO[0] + CharacterPosition.INCORRECT_POSITION.getIndicator() + INTERFACE_INFO[1] + INTERFACE_INFO[2] +
                INTERFACE_INFO[0] + CharacterPosition.CORRECT_POSITION.getIndicator() + INTERFACE_INFO[1] + INTERFACE_INFO[3] +
                INTERFACE_INFO[0] + CharacterPosition.MISSING_IN_WORD.getIndicator() + INTERFACE_INFO[1] + INTERFACE_INFO[4];
    }

}
