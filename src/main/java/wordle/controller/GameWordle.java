package wordle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wordle.controller.validators.WordleRule;
import wordle.utils.exceptions.GameException;

/**
 * Класс игры Wordle
 * Отвечает за состояние игры
 */
@Component
public class GameWordle {

    private final WordleRule gameRule;

    private String hiddenWord;

    private int countSteps = 1;

    @Autowired
    public GameWordle(WordleRule gameRule) {
        this.gameRule = gameRule;
    }

    public void startGame() throws GameException {
        this.hiddenWord = gameRule.getRuleDictionary().getRandomWord();
    }

    /**
     * Геттер
     *
     * @return возвращает кол-во шагов на данный момент
     */
    public int getCountSteps() {
        return countSteps;
    }

    /**
     * Увеличивает счетчик ходов на данный момент
     */
    public void incrementCountSteps() {
        countSteps++;
    }

    /**
     * Геттер
     *
     * @return возвращает правила заданной игры
     */
    public WordleRule getGameRule() {
        return gameRule;
    }

    /**
     * Перезапускает игру с новым загаданным словом
     */
    public void restartGame() throws GameException {
        countSteps = 1;
        hiddenWord = gameRule.getRuleDictionary().getRandomWord();
    }

    /**
     * Геттер
     *
     * @return - возращает загаданное слово
     */
    public String getHiddenWord() {
        return hiddenWord;
    }
}
