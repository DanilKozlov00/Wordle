package wordle.controller.validators;

import wordle.model.Dictionary;
import wordle.view.CharacterPosition;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс отвечающий за проверку вводимых пользователем слов правилам игры
 */
public class WordleRule {

    public static final int WORD_LENGTH = 5;
    private static final int MAX_STEPS = 6;
    private final Dictionary dictionary;

    public WordleRule(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Dictionary getRuleDictionary() {
        return dictionary;
    }

    /**
     * Метод проверяющий положение букв в загаданном слове с положением букв в веденном слове
     *
     * @param inputWord  - слово введенное пользователем
     * @param hiddenWord - загаданное слово
     * @return - возвращает список пар<Символ, Положение в слове>
     */
    public List<AbstractMap.SimpleEntry<Character, CharacterPosition>> checkCharactersPosition(String inputWord, String hiddenWord) throws IOException {
        List<AbstractMap.SimpleEntry<Character, CharacterPosition>> result = new LinkedList<>();
        if (isCorrectWord(inputWord)) {
            for (int i = 0; i < inputWord.length(); i++) {
                char characterToCheck = inputWord.toLowerCase().charAt(i);
                if (hiddenWord.indexOf(characterToCheck) != -1) {
                    if (hiddenWord.charAt(i) == characterToCheck) {
                        result.add(new AbstractMap.SimpleEntry<>(characterToCheck, CharacterPosition.CORRECT_POSITION));
                    } else {
                        result.add(new AbstractMap.SimpleEntry<>(characterToCheck, CharacterPosition.INCORRECT_POSITION));
                    }
                } else {
                    result.add(new AbstractMap.SimpleEntry<>(characterToCheck, CharacterPosition.MISSING_IN_WORD));
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
    public boolean isCorrectWord(String inputWord) throws IOException {
        if (inputWord.length() != WORD_LENGTH) {
            return false;
        }
        return dictionary.containsWord(inputWord.toLowerCase());
    }

    /**
     * Проверка что текущий шаг является играбельным
     *
     * @param step - значение текущего шага
     * @return - true, если значение шага не превышает лимит шагов, иначе false
     */
    public boolean isValidStep(int step) {
        return step > MAX_STEPS;
    }

}
