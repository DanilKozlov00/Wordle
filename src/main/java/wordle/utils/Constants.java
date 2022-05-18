package wordle.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    private final String FILE_NOT_FOUND;
    private final String ERROR_WHILE_READING_FILE;
    private final String DICTIONARY_IS_EMPTY;

    public Constants(@Value("${Constants.FILE_NOT_FOUND}") String FILE_NOT_FOUND,
                     @Value("${Constants.ERROR_WHILE_READING_FILE}") String ERROR_WHILE_READING_FILE,
                     @Value("${Constants.DICTIONARY_IS_EMPTY}") String DICTIONARY_IS_EMPTY) {
        this.FILE_NOT_FOUND = FILE_NOT_FOUND;
        this.ERROR_WHILE_READING_FILE = ERROR_WHILE_READING_FILE;
        this.DICTIONARY_IS_EMPTY = DICTIONARY_IS_EMPTY;
    }

    public String getFILE_NOT_FOUND() {
        return FILE_NOT_FOUND;
    }

    public String getERROR_WHILE_READING_FILE() {
        return ERROR_WHILE_READING_FILE;
    }

    public String getDICTIONARY_IS_EMPTY() {
        return DICTIONARY_IS_EMPTY;
    }

}
