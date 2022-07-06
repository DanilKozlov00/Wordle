package wordle.model.exceptions;

public class GameException extends RuntimeException {

    public static final String DICTIONARY_IS_EMPTY = "Dictionary is empty";

    public GameException(String message) {
        super(message);
    }
}
