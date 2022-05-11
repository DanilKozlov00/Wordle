package wordle.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wordle.utils.exceptions.GameException;
import wordle.view.WordleInterface;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static wordle.Main.ENGLISH_TXT_DICTIONARY_PATH;
import static wordle.utils.Constants.*;

/**
 * Реализация текстового словаря
 */
@Component
public class TxtDictionary implements Dictionary {

    private static final int DEFAULT_PAGE_SIZE = 1000;

    private final String dictionaryFileName;

    @Autowired
    public TxtDictionary(@Value(ENGLISH_TXT_DICTIONARY_PATH) String dictionaryFileName) {
        this.dictionaryFileName = dictionaryFileName;
    }

    @Override
    public String getRandomWord() throws GameException {
        int countFileLine = getCountFileLines();
        if (countFileLine == 0) {
            throw new GameException(DICTIONARY_IS_EMPTY);
        }
        int randomWordLine = new Random().nextInt(countFileLine);
        int randomWordPage = randomWordLine / DEFAULT_PAGE_SIZE;
        return readPage(randomWordLine / DEFAULT_PAGE_SIZE).get(randomWordLine - DEFAULT_PAGE_SIZE * randomWordPage);
    }

    @Override
    public boolean isContainsWord(String word) throws GameException {
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

    private List<String> readPage(int page) throws GameException {
        List<String> pageWords = new ArrayList<>();
        try (BufferedReader reader = getReader()) {
            int currentPage = 0;
            int readWords = 0;
            while (reader.readLine() != null && currentPage != page) {
                readWords++;
                if (readWords == DEFAULT_PAGE_SIZE) {
                    readWords = 0;
                    currentPage++;
                }
            }
            String line;
            if (currentPage == page) {
                while ((line = reader.readLine()) != null && pageWords.size() != DEFAULT_PAGE_SIZE) {
                    pageWords.add(line);
                }
            }
        } catch (FileNotFoundException fileNotFoundException) {
            throw new GameException(FILE_NOT_FOUND);
        } catch (IOException ioException) {
            throw new GameException(ERROR_WHILE_READING_FILE);
        }

        return pageWords;
    }

    private BufferedReader getReader() throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(dictionaryFileName), StandardCharsets.UTF_8));
    }

    private int getCountFileLines() throws GameException {
        try (BufferedReader reader = getReader()) {
            int count = 0;
            while (reader.readLine() != null) {
                count++;
            }
            return count;
        } catch (FileNotFoundException fileNotFoundException) {
            throw new GameException(FILE_NOT_FOUND);
        } catch (IOException ioException) {
            throw new GameException(ERROR_WHILE_READING_FILE);
        }
    }
}
