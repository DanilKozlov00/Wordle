package wordle.game;

public enum CharacterPosition {

    CORRECT_POSITION("Y"),
    INCORRECT_POSITION("N"),
    MISSING_IN_WORD("X");

    private final String indicator;

    public String getIndicator() {
        return indicator;
    }

    CharacterPosition(String indicator) {
        this.indicator = indicator;
    }
}
