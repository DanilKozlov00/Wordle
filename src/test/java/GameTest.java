import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTest {

    Game game;

    public static void main(String[] args) {
        GameTest gameTest = new GameTest();
        gameTest.testLoseGame();
        System.out.println("1/6 test passed");
        gameTest.testWinGameInRandomInput();
        System.out.println("2/6 test passed");
        gameTest.testWinGameInLastStep();
        System.out.println("3/6 test passed");
        gameTest.testIncorrectWord();
        System.out.println("4/6 test passed");
        gameTest.testIncorrectInput();
        System.out.println("5/6 test passed");
        gameTest.testWinGameInOneStep();
        System.out.println("6/6 test passed");
    }

    @Test
    public void testIncorrectWord() {
        game = new Game(new TxtDictionary("src/main/resources/dictionaries/dictionary.txt"), "toast");
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("their"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("again"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("there"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("pluto"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("after"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("words"));

    }

    @Test
    public void testIncorrectInput() {
        game = new Game(new TxtDictionary("src/main/resources/dictionaries/dictionary.txt"), "toast");
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("123"));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult(" "));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("hthtt"));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("dawdwdadsdsadwada sd f fef"));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("a"));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("\n"));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("вфввфы"));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("лодка"));
    }

    @Test
    public void testWinGameInOneStep() {
        game = new Game(new TxtDictionary("src/main/resources/dictionaries/dictionary.txt"), "toast");
        assertEquals(Game.GAME_WIN, game.getStepResult("toast"));
    }

    @Test
    public void testWinGameInLastStep() {
        game = new Game(new TxtDictionary("src/main/resources/dictionaries/dictionary.txt"), "again");
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("their"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("there"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("pluto"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("after"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("words"));
        assertEquals(Game.GAME_WIN, game.getStepResult("again"));
    }

    @Test
    public void testWinGameInRandomInput() {
        game = new Game(new TxtDictionary("src/main/resources/dictionaries/dictionary.txt"), "again");
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("play"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("their"));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("hthtt"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("pluto"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("after"));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("dawdwdadsdsadwada sd f fef"));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("dawdwdadsdsadwada sd f fef"));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("dawdwdadsdsadwada sd f fef"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("words"));
        assertEquals(Game.GAME_WIN, game.getStepResult("again"));
    }

    @Test
    public void testLoseGame() {
        game = new Game(new TxtDictionary("src/main/resources/dictionaries/dictionary.txt"), "again");
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("play"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("their"));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("hthtt"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("pluto"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("after"));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("dawdwdadsdsadwada sd f fef"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("words"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("words"));
        assertEquals(Game.INCORRECT_INPUT, game.getStepResult("a"));
        assertEquals(Game.INCORRECT_WORD, game.getStepResult("these"));
        assertEquals(Game.GAME_LOSE, game.getStepResult("small"));
    }


}
