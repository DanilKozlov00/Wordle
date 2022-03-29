package wordleTest;

import wordle.TxtDictionary;
import wordle.Game;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest {

    Game game = new Game(new TxtDictionary("src/main/resources/dictionaries/dictionary.txt"), TOAST);

    private final static String TEST_PASSED = "/8 test passed";
    private final static String WORDS = "words";
    private final static String THEIR = "their";
    private final static String AFTER = "after";
    private final static String PLUTO = "pluto";
    private final static String TOAST = "toast";
    private final static String RANDOM_WORDS = "dawdwdadsdsadwada sd f fef";
    private final static String RANDOM_WORD = "hthtt";
    private final static String AGAIN = "again";
    private final static String PLAY = "play";
    private final static String A = "a";
    private final static String THERE = "there";

    public static void main(String[] args) {
        GameTest gameTest = new GameTest();
        gameTest.testIncorrectWord();
        System.out.println("1" + TEST_PASSED);
        gameTest.testIncorrectInput();
        System.out.println("2" + TEST_PASSED);
        gameTest.testWinGameInOneStep();
        System.out.println("3" + TEST_PASSED);
        gameTest.testWinGameInOneStepUpperCase();
        System.out.println("4" + TEST_PASSED);
        gameTest.testWinGameInOneStepRandomCase();
        System.out.println("5" + TEST_PASSED);
        gameTest.testWinGameInLastStep();
        System.out.println("6" + TEST_PASSED);
        gameTest.testWinGameInRandomInput();
        System.out.println("7" + TEST_PASSED);
        gameTest.testLoseGame();
        System.out.println("8" + TEST_PASSED);
    }

    @Test
    public void testIncorrectWord() {
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(THEIR));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(AGAIN));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(THERE));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(PLUTO));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(AFTER));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(WORDS));
    }

    @Test
    public void testIncorrectInput() {
        game.startNewGame(TOAST);
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("123"));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult(" "));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult(RANDOM_WORD));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult(RANDOM_WORDS));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult(A));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("\n"));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("вфввфы"));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("лодка"));
    }

    @Test
    public void testWinGameInOneStep() {
        game.startNewGame(TOAST);
        assertEquals(Game.GAME_WIN, game.getStepResult(TOAST));
    }

    @Test
    public void testWinGameInOneStepUpperCase() {
        game.startNewGame(TOAST);
        assertEquals(Game.GAME_WIN, game.getStepResult(TOAST.toUpperCase()));
    }

    @Test
    public void testWinGameInOneStepRandomCase() {
        game.startNewGame(TOAST);
        assertEquals(Game.GAME_WIN, game.getStepResult("ToASt"));
    }

    @Test
    public void testWinGameInLastStep() {
        game.startNewGame(AGAIN);
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(THEIR));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(THERE));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(PLUTO));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(AFTER));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(WORDS));
        assertEquals(Game.GAME_WIN, game.getStepResult(AGAIN));
    }

    @Test
    public void testWinGameInRandomInput() {
        game.startNewGame(AGAIN);
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult(PLAY));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(THEIR));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult(RANDOM_WORD));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(PLUTO));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(AFTER));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult(RANDOM_WORDS));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult(RANDOM_WORDS));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult(RANDOM_WORDS));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(WORDS));
        assertEquals(Game.GAME_WIN, game.getStepResult(AGAIN));
    }

    @Test
    public void testLoseGame() {
        game.startNewGame(TOAST);
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult(PLAY));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(THEIR));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult(RANDOM_WORD));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(PLUTO));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(AFTER));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult(RANDOM_WORDS));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(WORDS));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult(WORDS));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult(A));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("these"));
        assertEquals(Game.GAME_LOSE, game.getStepResult("small"));
        assertEquals(Game.GAME_LOSE, game.getStepResult(WORDS));
    }


}
