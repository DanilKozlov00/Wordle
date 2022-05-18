package wordle.controller.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wordle.model.Dictionary;
import wordle.utils.exceptions.GameException;
import wordle.controller.CharacterPosition;

import java.util.AbstractMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс отвечающий за проверку вводимых пользователем слов правилам игры
 */
@Component
public class WordleRule {

    public final int WORD_LENGTH;
    private final int MAX_STEPS;
    private final Dictionary dictionary;

    @Autowired
    public WordleRule(Dictionary dictionary, @Value("${WordleRule.WORD_LENGTH}") int WORD_LENGTH,@Value("${WordleRule.MAX_STEPS}") int MAX_STEPS) {
        this.dictionary = dictionary;
        this.WORD_LENGTH = WORD_LENGTH;
        this.MAX_STEPS = MAX_STEPS;
    }

    public int getWORD_LENGTH() {
        return WORD_LENGTH;
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
    public List<AbstractMap.SimpleEntry<Character, CharacterPosition>> checkCharactersPosition(String inputWord, String hiddenWord) throws GameException {
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
    public boolean isCorrectWord(String inputWord) throws GameException {
        if (inputWord.length() != WORD_LENGTH) {
            return false;
        }
        return dictionary.isContainsWord(inputWord.toLowerCase());
    }

    /**
     * Проверка что текущий шаг является играбельным
     *
     * @param step - значение текущего шага
     * @return - true, если значение шага не превышает лимит шагов, иначе false
     */
    public boolean isInvalidStep(int step) {
        return step >= MAX_STEPS;
    }

}
