package wordle;

/**
 * Реализация интерфеса положения букв в слове для YNX символов
 */
public class CharactersIndicatorsImpl implements CharactersIndicators {

    @Override
    public String getCorrectCharacter() {
        return "Y";
    }

    @Override
    public String getMissingInWordCharacter() {
        return "N";
    }

    @Override
    public String getIncorrectPositionCharacter() {
        return "X";
    }
}
