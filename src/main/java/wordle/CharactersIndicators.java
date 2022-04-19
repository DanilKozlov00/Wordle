package wordle;

/**
 * Интерфейс определяющий индикаторы положения буквы в слове
 */
public interface CharactersIndicators {

    /**
     * @return возвращает индикатор корректного положения буквы в слове
     */
    String getCorrectCharacter();

    /**
     * @return возвращает индикатор отсутствия буквы в слове
     */
    String getMissingInWordCharacter();

    /**
     * @return возвращает индикатор некорректного положения буквы в слове
     */
    String getIncorrectPositionCharacter();
}
