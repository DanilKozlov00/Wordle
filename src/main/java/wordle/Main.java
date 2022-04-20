package wordle;

import wordle.model.Dictionary;
import wordle.model.TxtDictionary;
import wordle.controller.GameWordle;
import wordle.view.WordleInterface;
import wordle.controller.validators.WordleRule;

import java.io.FileNotFoundException;
import java.io.IOException;

import static wordle.utils.Constants.*;

public class Main {

    public static final String ENGLISH_TXT_DICTIONARY_PATH = "src/main/resources/dictionaries/dictionary.txt";

    public static void main(String[] args) {
        try {
            Dictionary dictionary = new TxtDictionary(ENGLISH_TXT_DICTIONARY_PATH);
            WordleRule gameRule = new WordleRule(dictionary);
            GameWordle gameWordle = new GameWordle(gameRule);
            WordleInterface wordleInterface = new WordleInterface(gameWordle);
            wordleInterface.startGame();
        } catch (FileNotFoundException fileNotFoundException) {
            System.err.println(FILE_NOT_FOUND);
        } catch (IOException ioException) {
            System.err.println(ERROR_WHILE_READING_FILE);
        }
    }
}
