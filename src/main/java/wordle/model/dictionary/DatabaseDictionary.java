package wordle.model.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wordle.model.dto.Word;
import wordle.model.exceptions.GameException;
import wordle.services.dao.WordDao;

import static wordle.model.exceptions.GameException.DICTIONARY_IS_EMPTY;

@Service
public class DatabaseDictionary implements Dictionary {

    private final String dictionaryName;
    @Autowired
    WordDao wordDao;

    public DatabaseDictionary(@Value("${dictionary.path.English_Dictionary_Name}") String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    @Override
    public boolean isContainsWord(String word) {
        return wordDao.getWordFromDictionaryByDictionaryName(dictionaryName, word) != null;
    }

    @Override
    public String getRandomWord() throws GameException {
        Word word = wordDao.getRandomWordFromDictionaryByDictionaryName(dictionaryName);
        if (word == null) {
            throw new GameException(DICTIONARY_IS_EMPTY);
        }
        return word.getWord();
    }
}

