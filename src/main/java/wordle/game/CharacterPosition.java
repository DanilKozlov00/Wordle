package wordle.game;

/**
 * Перечесление для обозначения позиции символа в слове
 */

public enum CharacterPosition {

    CORRECT_POSITION("Y"),
    INCORRECT_POSITION("N"),
    MISSING_IN_WORD("X");

    private final String indicator;

    /**
     * @return строка индикации положения символа в слове
     */
    public String getIndicator() {
        return indicator;
    }

    CharacterPosition(String indicator) {
        this.indicator = indicator;
    }
}
