package wordle.security;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;


public class JwtAuthenticationException extends AuthenticationException {
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    private HttpStatus httpStatus;

    public JwtAuthenticationException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }
}
