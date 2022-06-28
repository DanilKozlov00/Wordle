package wordle.model.dictionary;

import wordle.model.exceptions.GameException;

/**
 * интерфейс словаря
 */
public interface Dictionary {

    /**
     * @param word - слово для поиска
     * @return находится ли слово в словаре
     */
    boolean isContainsWord(String word) throws GameException;

    /**
     * @return случайное слово из словаря
     * @throws - ошибка чтения из словаря
     */
    String getRandomWord() throws GameException;
}
