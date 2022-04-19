package wordle.game;

import wordle.dictionary.Dictionary;
import wordle.game.interfaceNotations.CharactersIndicators;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс отвечающий за проверку вводимых пользователем слов правилам игры
 */
public class WordleRule {

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

    public Dictionary getRuleDictionary() {
        return dictionary;
    }

    /**
     * Возвращает результат шага игрока
     *
     * @param inputWord  - слово введеное пользователем
     * @param hiddenWord - загаданное слово
     * @param step       - шаг игрока на данный момент
     * @return - Возвращает результат игры на данном шаге
     */
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
    public List<AbstractMap.SimpleEntry<Character, String>> checkCharactersPosition(String inputWord, String hiddenWord, CharactersIndicators charactersIndicators) {
        List<AbstractMap.SimpleEntry<Character, String>> result = new LinkedList<>();
        if (isCorrectWord(inputWord)) {
            for (int i = 0; i < inputWord.length(); i++) {
                char characterToCheck = inputWord.toLowerCase().charAt(i);
                if (hiddenWord.indexOf(characterToCheck) != -1) {
                    if (hiddenWord.charAt(i) == characterToCheck) {
                        result.add(new AbstractMap.SimpleEntry<>(characterToCheck, charactersIndicators.getCorrectCharacter()));
                    } else {
                        result.add(new AbstractMap.SimpleEntry<>(characterToCheck, charactersIndicators.getIncorrectPositionCharacter()));
                    }
                } else {
                    result.add(new AbstractMap.SimpleEntry<>(characterToCheck, charactersIndicators.getMissingInWordCharacter()));
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
