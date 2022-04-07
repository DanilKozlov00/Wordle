package wordle;

import java.util.List;

public interface Dictionary {
    List<String> readDictionary();
    boolean containsWord(String word);
}
