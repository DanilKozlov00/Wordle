package wordle.services.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wordle.controller.GameWordle;
import wordle.controller.validators.WordleRule;
import wordle.model.dictionary.WordCharacter;
import wordle.model.exceptions.GameException;

import java.util.ArrayList;
import java.util.List;

import static wordle.view.WordleInterface.*;

@Service
public class GameService {

    private static final String WORD_IS_CORRECT = "Word is correct";
    private static final String WORD_IS_NOT_CONTAINS_IN_DICTIONARY = "Word is not contains in dictionary";

    private final GameWordle gameWordle;
    private final WordleRule wordleRule;

    @Autowired
    public GameService(GameWordle gameWordle) {
        this.gameWordle = gameWordle;
        wordleRule = gameWordle.getGameRule();
    }

    public void restartGame() throws GameException {
        gameWordle.restartGame();
    }


    public String getHiddenWord() {
        return gameWordle.getHiddenWord();
    }

    public String getCheckWordStatus(String word) throws GameException {
        if (wordleRule.isCorrectWord(word)) {
            return WORD_IS_CORRECT;
        }
        if (word.length() != wordleRule.getWordLength()) {
            return INCORRECT_WORD_LENGTH;
        }
        return WORD_IS_NOT_CONTAINS_IN_DICTIONARY;
    }

    public String getGameStatus(String word, int step) {
        if (!wordleRule.isInvalidStep(step)) {
            if (word.equalsIgnoreCase(gameWordle.getHiddenWord())) {
                return GAME_WIN;
            }
            if (!wordleRule.isInvalidStep(step + 1)) {
                return IN_GAME;
            }
        }
        return GAME_LOSE;
    }

    public List<WordCharacter> getWordIndices(String word) throws GameException {
        if (word.length() != wordleRule.getWordLength()) {
            return new ArrayList<>();
        }
        return wordleRule.checkCharactersPosition(word, gameWordle.getHiddenWord());
    }

}
