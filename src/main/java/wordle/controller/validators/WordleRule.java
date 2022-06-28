package wordle.controller.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import wordle.model.dictionary.DatabaseDictionary;
import wordle.model.dictionary.Dictionary;
import wordle.model.dictionary.WordCharacter;
import wordle.model.exceptions.GameException;
import wordle.controller.CharacterPosition;

import java.util.LinkedList;
import java.util.List;

/**
 * Класс отвечающий за проверку вводимых пользователем слов правилам игры
 */
@Component
public class WordleRule {

    public static final int WORD_LENGTH = 5;
    private static final int MAX_STEPS = 6;
    private final Dictionary dictionary;

    @Autowired
    public WordleRule(@Qualifier("databaseDictionary") Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public int getWordLength() {
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
    public List<WordCharacter> checkCharactersPosition(String inputWord, String hiddenWord) throws GameException {
        List<WordCharacter> result = new LinkedList<>();
        if (isCorrectWord(inputWord)) {
            for (int i = 0; i < inputWord.length(); i++) {
                char characterToCheck = inputWord.toLowerCase().charAt(i);
                if (hiddenWord.indexOf(characterToCheck) != -1) {
                    if (hiddenWord.charAt(i) == characterToCheck) {
                        result.add(new WordCharacter(characterToCheck, CharacterPosition.CORRECT_POSITION));
                    } else {
                        result.add(new WordCharacter(characterToCheck, CharacterPosition.INCORRECT_POSITION));
                    }
                } else {
                    result.add(new WordCharacter(characterToCheck, CharacterPosition.MISSING_IN_WORD));
                }
            }
        }
        return result;
    }

    /**
     * Проверка корректности введенного слова
     *
     * @param inputWord - слово введенное пользователем
     * @return - соответствует ли слово правилам игры
     */
    public boolean isCorrectWord(String inputWord) throws GameException {
        if (inputWord.length() != WORD_LENGTH) {
            return false;
        }
        return dictionary.isContainsWord(inputWord);
    }

    /**
     * Проверка что текущий шаг является играбельным
     *
     * @param step - значение текущего шага
     * @return - true, если значение шага не превышает лимит шагов, иначе false
     */
    public boolean isInvalidStep(int step) {
        return step > MAX_STEPS;
    }

}
