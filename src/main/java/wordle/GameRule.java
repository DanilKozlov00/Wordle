package wordle;

import java.util.AbstractMap;
import java.util.List;

public interface GameRule {
    String getRulesInfo();
    String getStepResult(String inputWord, String hiddenWord, int step);
    List<AbstractMap.SimpleEntry<Character, String>> getCharactersPosition(String inputWord, String hiddenWord);
    Dictionary getRuleDictionary();
}
