//package wordleTest;
//
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvFileSource;
//import wordle.dictionary.TxtDictionary;
//import wordle.game.GameWordle;
//import wordle.game.WordleInterface;
//import wordle.game.WordleRule;
//import wordle.game.interfaceNotations.CharactersIndicators;
//import wordle.game.interfaceNotations.CharactersIndicatorsImpl;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static wordle.Main.ENGLISH_TXT_DICTIONARY_PATH;
//
//public class ParametrizedTest {
//
//    private static final GameWordle gameWordle = new GameWordle(new WordleRule(new TxtDictionary(ENGLISH_TXT_DICTIONARY_PATH)), "toast");
//    private static final CharactersIndicators CHARACTERS_INDICATORS = new CharactersIndicatorsImpl();
//    private static final WordleInterface gameInterface = new WordleInterface(gameWordle, CHARACTERS_INDICATORS);
//
//    @ParameterizedTest(name = "#{index} - InputWord={0},StepResult={1}")
//    @CsvFileSource(resources = "/testData/data.csv", delimiter = ';')
//    void testStepResult(String inputWord, String stepResult) {
//        String gameResult = gameInterface.inputWordStepResult(inputWord);
//        assertEquals(stepResult, gameResult);
//    }
//}

