package wordle.dictionary;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Реализация текстового словаря
 */
public class TxtDictionary implements Dictionary {

    private static final int DEFAULT_PAGE_SIZE = 1000;

    private final String dictionaryFileName;

    public TxtDictionary(String dictionaryFileName) {
        this.dictionaryFileName = dictionaryFileName;
    }

    @Override
    public String readRandomWord() throws IOException {
        int countFileLine = getCountFileLines();
        int randomWordLine = new Random().nextInt(countFileLine);
        int randomWordPage = randomWordLine / DEFAULT_PAGE_SIZE;
        return readPage(randomWordLine / DEFAULT_PAGE_SIZE).get(randomWordLine - DEFAULT_PAGE_SIZE * randomWordPage);
    }

    @Override
    public boolean containsWord(String word) throws IOException {
        int currentPage = 0;
        List<String> currentPageWords;
        while (!(currentPageWords = readPage(currentPage)).isEmpty()) {
            currentPage++;
            if (currentPageWords.contains(word)) {
                return true;
            }
        }
        return false;
    }

    private List<String> readPage(int page) throws IOException {
        BufferedReader reader = getReader();
        int currentPage = 0;
        int readWords = 0;
        while (reader.readLine() != null && currentPage != page) {
            readWords++;
            if (readWords == DEFAULT_PAGE_SIZE) {
                readWords = 0;
                currentPage++;
            }
        }
        List<String> pageWords = new ArrayList<>();
        String line;
        if (currentPage == page) {
            while ((line = reader.readLine()) != null && pageWords.size() != DEFAULT_PAGE_SIZE) {
                pageWords.add(line);
            }
        }
        return pageWords;
    }

    private BufferedReader getReader() throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(dictionaryFileName), StandardCharsets.UTF_8));
    }

    private int getCountFileLines() throws IOException {
        BufferedReader reader = getReader();
        int count = 0;
        while (reader.readLine() != null) {
            count++;
        }
        return count;
    }
}
