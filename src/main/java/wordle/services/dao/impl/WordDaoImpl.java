package wordle.services.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wordle.model.dto.Word;
import wordle.services.dao.DaoSessionFactory;
import wordle.services.dao.WordDao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Random;

@Repository
@Transactional
public class WordDaoImpl extends DaoSessionFactory implements WordDao {

    private static final String DICTIONARY_NAME = "dictionaryName";

    @Override
    public Word getWordFromDictionaryByDictionaryName(String dictionaryName, String word) {
        try {
            return getCurrentSession().createQuery("from Word as w where w.dictionary.name=:dictionaryName and w.word=:word", Word.class)
                    .setParameter(DICTIONARY_NAME, dictionaryName)
                    .setParameter("word", word)
                    .getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }

    @Override
    public Word getRandomWordFromDictionaryByDictionaryName(String dictionaryName) {
        Word result = null;
        TypedQuery<Long> q = getCurrentSession().createQuery("select  count (*) from Word as w where w.dictionary.name=:dictionaryName", Long.class).setParameter(DICTIONARY_NAME, dictionaryName);
        int count = q.getSingleResult().intValue();
        if (0 != count) {
            int index = new Random().nextInt(count);
            TypedQuery<Word> query = getCurrentSession().createQuery("from Word as w where w.dictionary.name=:dictionaryName", Word.class)
                    .setParameter(DICTIONARY_NAME, dictionaryName)
                    .setFirstResult(index)
                    .setMaxResults(1);
            result = query.getSingleResult();
        }
        return result;
    }
}
