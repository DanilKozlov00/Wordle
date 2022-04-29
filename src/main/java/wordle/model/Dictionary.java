package wordle.model;

import wordle.utils.exceptions.GameException;

/**
 * Интерфейс словаря
 */
public interface Dictionary {

    /**
     * @param word - слово для поиска
     * @return находится ли слово в словаре
     */
    boolean isContainsWord(String word);

    /**
     * @return случайное слово из словаря
     * @exception - ошибка чтения из словаря
     */
    String getRandomWord() throws GameException;
}
