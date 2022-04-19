package wordle;

/**
 * Класс игры Wordle
 * Отвечает за состояние игры
 */
public class GameWordle {

    private final WordleRule gameRule;

    private String hiddenWord;
    private int countSteps = 0;

    public GameWordle(WordleRule gameRule, String hiddenWord) {
        this.gameRule = gameRule;
        this.hiddenWord = hiddenWord;
    }

    public GameWordle(WordleRule gameRule) {
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
    public void restartGame() {
        countSteps = 0;
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
