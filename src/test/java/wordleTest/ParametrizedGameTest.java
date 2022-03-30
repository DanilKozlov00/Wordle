package wordleTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import wordle.Dictionary;
import wordle.Game;
import wordle.TxtDictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ParametrizedGameTest {
    private String inputWord;
    private String stepResult;
    private Impostor game = Impostor.getImpostor();

    public ParametrizedGameTest(String inputWord, String stepResult) {
        super();
        this.inputWord = inputWord;
        this.stepResult = stepResult;
    }

    @Parameterized.Parameters()
    public static List<Object[]> inputData() {
        List<Object[]> parametersProvided = new ArrayList<>();
        for (String line : readFromCSV("src/test/resources/testData/data.csv")) {
            String[] valueWords = line.split(";");
            parametersProvided.add(new Object[]{valueWords[0], valueWords[1]});
        }
        return parametersProvided;
    }

    @Test
    public void stepTest() {
        String gameResult = game.getStepResult(inputWord);
        assertEquals(stepResult, gameResult);

        if (stepResult.equals(Game.GAME_LOSE) || stepResult.equals(Game.GAME_WIN)) {
            game.startNewGame("toast");
        }
    }

    private static List<String> readFromCSV(String pathToCsv) {
        List<String> fileLines = new ArrayList<>();
        try (BufferedReader csvReader = new BufferedReader(new FileReader(pathToCsv))) {
            String row;
            while ((row = csvReader.readLine()) != null) {
                fileLines.add(row);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return fileLines;
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
