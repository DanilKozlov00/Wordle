package wordleTest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import wordle.Game;
import wordle.TxtDictionary;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParametrizedTest {

    private static final Game game = new Game(new TxtDictionary("src/main/resources/dictionaries/dictionary.txt"), "toast");

    @ParameterizedTest(name="#{index} - InputWord={0},StepResult={1}")
    @CsvFileSource(resources = "/testData/data.csv", delimiter = ';')
    void testStepResult(String inputWord, String stepResult) {
        String gameResult = game.getStepResult(inputWord);
        assertEquals(stepResult, gameResult);

        if (stepResult.equals(Game.GAME_LOSE) || stepResult.equals(Game.GAME_WIN)) {
            game.startNewGame("toast");
        }
    }
}

