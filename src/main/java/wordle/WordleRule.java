package wordle;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс отвечающий за проверку вводимых пользователем слов правилам игры
 */
public class WordleRule implements GameRule {

    private enum CharacterPosition {
        CORRECT_POSITION("Y"),
        INCORRECT_POSITION("N"),
        MISSING_IN_WORD("X");

        private final String indicator;

        public String getIndicator() {
            return indicator;
        }

        CharacterPosition(String indicator) {
            this.indicator = indicator;
        }
    }

    public static final String INCORRECT_WORD = "Incorrect word";
    public static final String INCORRECT_INPUT = "Incorrect input, try another word!";
    public static final String GAME_WIN = "Game is win";
    public static final String GAME_LOSE = "Game is lose";

    private static final int WORD_LENGTH = 5;
    private static final int MAX_STEPS = 6;

    private final Dictionary dictionary;

    public WordleRule(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    @Override
    public Dictionary getRuleDictionary() {
        return dictionary;
    }

    /**
     * Описывает правила игры
     *
     * @return - Возвращает строку с правилами игры
     */
    @Override
    public String getRulesInfo() {
        return "You have six tries to guess the five-letter Wordle.\nThe character below letter change after you submit your word.\nThe 'N' character indicates that you picked the right letter but it’s in the wrong spot.\nThe 'Y' character indicates that you picked the right letter in the correct spot.\nThe 'N' character indicates that the letter you picked is not included in the word at all.";
    }

    /**
     * Возвращает результат шага игрока
     *
     * @param inputWord  - слово введеное пользователем
     * @param hiddenWord - загаданное слово
     * @param step       - шаг игрока на данный момент
     * @return - Возвращает результат игры на данном шаге
     */
    @Override
    public String getStepResult(String inputWord, String hiddenWord, int step) {
        if (isCorrectWord(inputWord)) {
            if (step >= MAX_STEPS) {
                return GAME_LOSE;
            }
            if (!inputWord.equals(hiddenWord)) {
                return INCORRECT_WORD;
            }
            return GAME_WIN;
        } else {
            return INCORRECT_INPUT;
        }
    }

    /**
     * Метод проверяющий положение букв в загаданном слове с положением букв в веденном слове
     *
     * @param inputWord  - слово введенное пользователем
     * @param hiddenWord - загаданное слово
     * @return - возвращает список пар<Символ, Положение в слове>
     */
    public List<AbstractMap.SimpleEntry<Character, String>> getCharactersPosition(String inputWord, String hiddenWord) {
        List<AbstractMap.SimpleEntry<Character, String>> result = new LinkedList<>();
        if (isCorrectWord(inputWord)) {
            for (int i = 0; i < inputWord.length(); i++) {
                char characterToCheck = inputWord.toLowerCase().charAt(i);
                if (hiddenWord.indexOf(characterToCheck) != -1) {
                    if (hiddenWord.charAt(i) == characterToCheck) {
                        result.add(new AbstractMap.SimpleEntry<>(characterToCheck, CharacterPosition.CORRECT_POSITION.getIndicator()));
                    } else {
                        result.add(new AbstractMap.SimpleEntry<>(characterToCheck, CharacterPosition.INCORRECT_POSITION.getIndicator()));
                    }
                } else {
                    result.add(new AbstractMap.SimpleEntry<>(characterToCheck, CharacterPosition.MISSING_IN_WORD.getIndicator()));
                }
            }
        }
        return result;
    }

    /**
     * Проверка корректности введенного слова
     *
     * @param inputWord - слово введеное пользователем
     * @return - соответствует ли слово правилам игры
     */
    private boolean isCorrectWord(String inputWord) {
        if (inputWord.length() != WORD_LENGTH) {
            return false;
        }
        return dictionary.containsWord(inputWord.toLowerCase());
    }
}
