package wordle.dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Реализация тестового словаря
 */
public class TxtDictionary implements Dictionary {

    private final String dictionaryFileName;
    private int defaultPageSize = 1000;

    public TxtDictionary(String dictionaryFileName) {
        this.dictionaryFileName = dictionaryFileName;
    }

    /**
     * Сеттер
     *
     * @param defaultPageSize - кол-во считываемых строк при чтении словаря
     */
    public void setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    @Override
    public String readRandomWord() {
        List<String> allWords = readDictionary();
        return allWords.get(new Random().nextInt(allWords.size()));
    }

    @Override
    public boolean containsWord(String word) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(dictionaryFileName), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(word)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    /**
     * Метод считывания из словаря
     *
     * @return возвращает список слов словаря
     */
    private List<String> readDictionary() {
        List<String> allWords = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(dictionaryFileName), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null || allWords.size() <= defaultPageSize) {
                allWords.add(line);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return allWords;
    }
}
