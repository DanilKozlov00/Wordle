package wordle.model.game;

public enum CheckWordStatus {
    WORD_IS_CORRECT("Word is correct"),
    INCORRECT_WORD_LENGTH("Incorrect word length"),
    WORD_IS_NOT_CONTAINS_IN_DICTIONARY("Word is not contains in dictionary");

    private final String status;

    CheckWordStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
