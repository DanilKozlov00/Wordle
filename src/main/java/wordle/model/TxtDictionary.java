package wordle.model;

import wordle.utils.exceptions.GameException;
import wordle.view.WordleInterface;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static wordle.utils.Constants.*;

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
    public boolean isContainsWord(String word) {
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

    private List<String> readPage(int page) {
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
            WordleInterface.printException(FILE_NOT_FOUND);
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            WordleInterface.printException(ERROR_WHILE_READING_FILE);
            ioException.printStackTrace();
        }

        return pageWords;
    }

    private BufferedReader getReader() throws FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(dictionaryFileName), StandardCharsets.UTF_8));
    }

    private int getCountFileLines() {
        try (BufferedReader reader = getReader()) {
            int count = 0;
            while (reader.readLine() != null) {
                count++;
            }
            return count;
        } catch (FileNotFoundException fileNotFoundException) {
            WordleInterface.printException(FILE_NOT_FOUND);
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            WordleInterface.printException(ERROR_WHILE_READING_FILE);
            ioException.printStackTrace();
        }

        return 0;
    }
}
