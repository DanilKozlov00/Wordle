package wordleTest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import wordle.WordleRule;
import wordle.GameWordle;
import wordle.TxtDictionary;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParametrizedTest {

    private static final GameWordle gameWordle = new GameWordle(new WordleRule(new TxtDictionary("src/main/resources/dictionaries/dictionary.txt")), "toast");

    @ParameterizedTest(name = "#{index} - InputWord={0},StepResult={1}")
    @CsvFileSource(resources = "/testData/data.csv", delimiter = ';')
    void testStepResult(String inputWord, String stepResult) {
        String gameResult = gameWordle.checkWord(inputWord);
        assertEquals(stepResult, gameResult);
    }
}

