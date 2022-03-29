package wordle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TxtDictionary implements Dictionary {

    private final String dictionaryFileName;

    @Override
    public String getDictionaryPath() {
        return dictionaryFileName;
    }

    public TxtDictionary(String dictionaryFileName) {
        this.dictionaryFileName = dictionaryFileName;
    }

    @Override
    public List<String> readDictionary() {
        List<String> allWords = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(dictionaryFileName), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                allWords.add(line);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return allWords;
    }

}
