package wordle.services.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wordle.controller.GameWordle;
import wordle.controller.validators.WordleRule;
import wordle.model.dictionary.WordCharacter;
import wordle.model.dto.Attempt;
import wordle.model.dto.Step;
import wordle.model.dto.User;
import wordle.model.exceptions.GameException;
import wordle.services.dao.UserDao;
import wordle.services.dao.impl.GameDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class GameService {

    public static final String INCORRECT_WORD_LENGTH = "Incorrect word length";
    public static final String GAME_WIN = "Game is win";
    public static final String IN_GAME = "In game";
    public static final String GAME_LOSE = "Game is lose";
    public static final String HIDDEN_WORD = "Hidden word";
    private static final String WORD_IS_CORRECT = "Word is correct";
    private static final String WORD_IS_NOT_CONTAINS_IN_DICTIONARY = "Word is not contains in dictionary";

    private final GameWordle gameWordle;
    private final WordleRule wordleRule;

    @Autowired
    GameDao gameDao;
    @Autowired
    UserDao userDao;

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

    public boolean saveGameResult(List<List<wordle.model.dto.WordCharacter>> wordCharacters, String email, Integer coins) {
        User user = userDao.getByEmail(email);
        if (user != null) {
            boolean isWin = coins > 0;
            boolean isAdminAccrued = false;
            Attempt attempt = new Attempt(java.time.LocalDate.now(), user.getId(), coins, isAdminAccrued, isWin);
            Set<Step> steps = new HashSet<>();
            int count = 0;
            for (List<wordle.model.dto.WordCharacter> wordCharacter : wordCharacters) {
                Step step = new Step();
                step.setWordCharacters(setStepInWordCharacters(wordCharacter, step));
                step.setNumber(count++);
                steps.add(step);
            }
            attempt.setSteps(steps);
            return gameDao.saveGameResult(attempt, user);
        }
        return false;
    }

    private List<wordle.model.dto.WordCharacter> setStepInWordCharacters(List<wordle.model.dto.WordCharacter> wordCharacters, Step step) {
        List<wordle.model.dto.WordCharacter> result = new LinkedList<>();
        for (wordle.model.dto.WordCharacter wordCharacter : wordCharacters) {
            wordCharacter.setStep(step);
            result.add(wordCharacter);
        }
        return result;
    }


}
