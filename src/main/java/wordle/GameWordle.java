package wordle;

import java.util.*;

import static wordle.WordleRule.*;

/**
 * Класс игры Wordle
 * Отвечает за инициализацию игры и взаимодествие с пользователем
 */
public class GameWordle {

    private final GameRule gameRule;
    private String hiddenWord;
    private int countSteps = 0;

    public GameWordle(GameRule gameRule, String hiddenWord) {
        this.gameRule = gameRule;
        this.hiddenWord = hiddenWord;
    }

    public GameWordle(GameRule gameRule) {
        this.gameRule = gameRule;
        List<String> allWords = gameRule.getRuleDictionary().readDictionary();
        hiddenWord = allWords.get(new Random().nextInt(allWords.size()));
    }

    /**
     * Возвращает состояние игры к первоначальному
     */
    public void restartGame() {
        countSteps = 0;
        List<String> allWords = gameRule.getRuleDictionary().readDictionary();
        hiddenWord = allWords.get(new Random().nextInt(allWords.size()));
    }

    /**
     * Геттер
     *
     * @return - возращает загаданное слово
     */
    public String getHiddenWord() {
        return hiddenWord;
    }

    /**
     * Описывает правила игры
     *
     * @return - Возвращает строку с правилами игры
     */
    public String getGameRules() {
        return gameRule.getRulesInfo();
    }

    /**
     * Метод отвечает за взаимодействие класса игры и пользователя
     * Пользователь вводит слово и получает результат по введенному слову
     */
    public String checkWord(String inputWord) {
        String stepResult = gameRule.getStepResult(inputWord, hiddenWord, countSteps);
        if (!stepResult.equals(INCORRECT_INPUT)) {
            countSteps++;
        }
        return stepResult;
    }

    /**
     * Метод отображающий положение букв в веденном слове. Корректное место, некорректное место, отсутствует в слове.
     */
    public void printCharactersPositions(String inputWord, String hiddenWord) {
        List<AbstractMap.SimpleEntry<Character, String>> charactersPositionsPairs = gameRule.getCharactersPosition(inputWord, hiddenWord);
        StringBuilder positions = new StringBuilder();
        for (AbstractMap.SimpleEntry<Character, String> pair : charactersPositionsPairs) {
            System.out.print(pair.getKey() + " ");
            positions.append(pair.getValue()).append(" ");
        }
        System.out.println();
        System.out.println(positions);
    }

}
