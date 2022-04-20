package wordle.controller;

import wordle.controller.validators.WordleRule;

import java.io.IOException;

/**
 * Класс игры Wordle
 * Отвечает за состояние игры
 */
public class GameWordle {

    private final WordleRule gameRule;

    private String hiddenWord;
    private int countSteps = 1;

    public GameWordle(WordleRule gameRule, String hiddenWord) {
        this.gameRule = gameRule;
        this.hiddenWord = hiddenWord;
    }

    public GameWordle(WordleRule gameRule) throws IOException {
        this(gameRule, gameRule.getRuleDictionary().readRandomWord());
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
    public void restartGame() throws IOException {
        countSteps = 1;
        hiddenWord = gameRule.getRuleDictionary().readRandomWord();
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
