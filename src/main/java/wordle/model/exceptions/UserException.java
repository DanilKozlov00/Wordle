package wordle.model.exceptions;

public class UserException extends Exception {

    public final static String INCORRECT_PASSWORD = "Incorrect password";
    public final static String USER_NOT_FOUND = "User not found";

    public UserException(String message) {
        super(message);
    }
}
