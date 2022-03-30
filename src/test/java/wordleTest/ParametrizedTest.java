package wordleTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import wordle.Dictionary;
import wordle.Game;
import wordle.TxtDictionary;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParametrizedTest {

    private Impostor game = Impostor.getImpostor();

    @ParameterizedTest()
    @CsvFileSource(resources = "/testData/data.csv", delimiter = ';')
    void testStepResult(String inputWord, String stepResult) {
        String gameResult = game.getStepResult(inputWord);
        assertEquals(stepResult, gameResult);

        if (stepResult.equals(Game.GAME_LOSE) || stepResult.equals(Game.GAME_WIN)) {
            game.startNewGame("toast");
        }
    }
}

class Impostor extends Game {

    private static final Impostor impostor = new Impostor(new TxtDictionary("src/main/resources/dictionaries/dictionary.txt"), "toast");

    public Impostor(Dictionary dictionary, String hiddenWord) {
        super(dictionary, hiddenWord);
    }

    public static Impostor getImpostor() {
        return impostor;
    }
}
