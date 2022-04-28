package wordle;

import wordle.model.Dictionary;
import wordle.model.TxtDictionary;
import wordle.controller.GameWordle;
import wordle.view.WordleInterface;
import wordle.controller.validators.WordleRule;

public class Main {

    public static final String ENGLISH_TXT_DICTIONARY_PATH = "src/main/resources/dictionaries/dictionary.txt";

    public static void main(String[] args) {
        Dictionary dictionary = new TxtDictionary(ENGLISH_TXT_DICTIONARY_PATH);
        WordleRule gameRule = new WordleRule(dictionary);
        GameWordle gameWordle = new GameWordle(gameRule);
        WordleInterface wordleInterface = new WordleInterface(gameWordle);
        wordleInterface.startGame();
    }
}
