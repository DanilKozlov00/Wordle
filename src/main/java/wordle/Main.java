package wordle;

import wordle.dictionary.Dictionary;
import wordle.dictionary.TxtDictionary;
import wordle.game.GameWordle;
import wordle.game.WordleInterface;
import wordle.game.WordleRule;
import wordle.game.interfaceNotations.CharactersIndicators;
import wordle.game.interfaceNotations.CharactersIndicatorsImpl;

public class Main {

    public static final String ENGLISH_TXT_DICTIONARY_PATH = "src/main/resources/dictionaries/dictionary.txt";

    public static void main(String[] args) {
        Dictionary dictionary = new TxtDictionary(ENGLISH_TXT_DICTIONARY_PATH);
        WordleRule gameRule = new WordleRule(dictionary);
        GameWordle gameWordle = new GameWordle(gameRule);
        CharactersIndicators charactersIndicators = new CharactersIndicatorsImpl();
        WordleInterface wordleInterface = new WordleInterface(gameWordle, charactersIndicators);
        wordleInterface.startGame();
    }
}
