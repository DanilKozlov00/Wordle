package wordle.services.dao;

import wordle.model.dto.Word;

public interface WordDao {

    Word getWordFromDictionaryByDictionaryName(String dictionaryName, String word);

    Word getRandomWordFromDictionaryByDictionaryName(String dictionaryName);

}
