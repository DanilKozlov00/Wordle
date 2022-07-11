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
import wordle.model.game.CheckWordStatus;
import wordle.model.game.GameStatus;
import wordle.services.dao.UserDao;
import wordle.services.dao.impl.GameDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class GameService {
    public static final String HIDDEN_WORD = "Hidden word";

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

    public CheckWordStatus getCheckWordStatus(String word) throws GameException {
        if (wordleRule.isCorrectWord(word)) {
            return CheckWordStatus.WORD_IS_CORRECT;
        }
        if (word.length() != wordleRule.getWordLength()) {
            return CheckWordStatus.INCORRECT_WORD_LENGTH;
        }
        return CheckWordStatus.WORD_IS_NOT_CONTAINS_IN_DICTIONARY;
    }

    public GameStatus getGameStatus(String word, int step) {
        if (!wordleRule.isInvalidStep(step)) {
            if (word.equalsIgnoreCase(gameWordle.getHiddenWord())) {
                return GameStatus.GAME_WIN;
            }
            if (!wordleRule.isInvalidStep(step + 1)) {
                return GameStatus.IN_GAME;
            }
        }
        return GameStatus.GAME_LOSE;
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
