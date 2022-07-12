package wordle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wordle.controller.validators.WordleRule;
import wordle.model.exceptions.GameException;

/**
 * Класс игры Wordle.
 * Отвечает за состояние игры
 */
@Component
public class GameWordle {

    private final WordleRule gameRule;

    private String hiddenWord;

    @Autowired
    public GameWordle(WordleRule gameRule) {
        this.gameRule = gameRule;
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
        hiddenWord = gameRule.getRuleDictionary().getRandomWord();
    }

    /**
     * Геттер
     *
     * @return - возвращает загаданное слово
     */
    public String getHiddenWord() {
        return hiddenWord;
    }
}
