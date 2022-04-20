package wordle.model;

import java.io.IOException;

/**
 * Интерфейс словаря
 */
public interface Dictionary {

    /**
     * @param word - слово для поиска
     * @return находится ли слово в словаре
     */
    boolean containsWord(String word) throws IOException;

    /**
     * @return случайное слово из словаря
     */
    String readRandomWord() throws IOException;
}
