package wordle.dictionary;

/**
 * Интерфейс словаря
 */
public interface Dictionary {

    /**
     * @param word - слово для поиска
     * @return находится ли слово в словаре
     */
    boolean containsWord(String word);

    /**
     * @return случайное слово из словаря
     */
    String readRandomWord();
}
