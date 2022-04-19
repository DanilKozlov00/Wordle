package wordle.game;

import wordle.game.interfaceNotations.CharactersIndicators;

import java.util.AbstractMap;
import java.util.List;
import java.util.Scanner;

import static wordle.game.WordleRule.*;

/**
 * Класс отвечает за взаимодействие игры с пользователем
 */
public class WordleInterface {

    private static final String START = "start";
    private static final String EXIT = "exit";
    private static final String INPUT_WORD = "Введите слово: ";
    private static final String HIDDEN_WORD = "Hidden word: ";
    private static final String END_GAME_MESSAGE = "Print 'start' to restart game or 'exit' to exit";
    private static final String EMPTY_STRING = "";
    private static final String SPACE_STRING = " ";
    private static final String[] INTERFACE_INFO = {"\nThe '", "' character indicates that you picked", " the right letter but it’s in the wrong spot.", " the right letter in the correct spot", " the letter is not included in the word at all."};

    private GameWordle gameWordle;
    private CharactersIndicators charactersIndicators;

    public WordleInterface(GameWordle gameWordle, CharactersIndicators charactersIndicators) {
        this.gameWordle = gameWordle;
        this.charactersIndicators = charactersIndicators;
    }

    /**
     * Метод старта игры. Отвечает за общение игры с пользователем
     */
    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        String stepResult = EMPTY_STRING, inputWord;
        System.out.println(getInterfaceNotations());
        do {
            while (!stepResult.equals(GAME_LOSE) && !stepResult.equals(GAME_WIN)) {
                System.out.println(INPUT_WORD);
                inputWord = scanner.nextLine();
                stepResult = inputWordStepResult(inputWord);
                printCharactersPositions(gameWordle.getGameRule().checkCharactersPosition(inputWord, gameWordle.getHiddenWord(), charactersIndicators));
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
    }

    /**
     * Метод отвечает за взаимодействие класса игры и пользователя
     * Пользователь вводит слово и получает результат по введенному слову
     */
    public String inputWordStepResult(String inputWord) {
        String stepResult = gameWordle.getGameRule().getStepResult(inputWord, gameWordle.getHiddenWord(), gameWordle.getCountSteps());
        if (!stepResult.equals(INCORRECT_INPUT)) {
            gameWordle.incrementCountSteps();
        }
        return stepResult;
    }

    /**
     * Метод отображающий положение букв в веденном слове. Корректное место, некорректное место, отсутствует в слове.
     */
    private void printCharactersPositions(List<AbstractMap.SimpleEntry<Character, String>> charactersPositionsPairs) {
        StringBuilder positions = new StringBuilder();
        for (AbstractMap.SimpleEntry<Character, String> pair : charactersPositionsPairs) {
            System.out.print(pair.getKey() + SPACE_STRING);
            positions.append(pair.getValue()).append(SPACE_STRING);
        }
        System.out.println();
        System.out.println(positions);
    }

    /**
     * @return возвращает обозначения положения букв для данного интерфейса
     */
    private String getInterfaceNotations() {
        return INTERFACE_INFO[0] + charactersIndicators.getIncorrectPositionCharacter() + INTERFACE_INFO[1] + INTERFACE_INFO[2] +
                INTERFACE_INFO[0] + charactersIndicators.getCorrectCharacter() + INTERFACE_INFO[1] + INTERFACE_INFO[3] +
                INTERFACE_INFO[0] + charactersIndicators.getMissingInWordCharacter() + INTERFACE_INFO[1] + INTERFACE_INFO[4];
    }

}
