package wordle.services.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wordle.model.dto.Word;
import wordle.services.dao.DaoSessionFactory;
import wordle.services.dao.WordDao;

import javax.persistence.NoResultException;
import java.util.Random;

@Repository
@Transactional
public class WordDaoImpl extends DaoSessionFactory implements WordDao {

    @Override
    public Word getWordFromDictionaryByDictionaryName(String dictionaryName, String word) {
        try {
            return getCurrentSession().createQuery("from Word as w where w.dictionary.name=:dictionaryName and w.word=:word", Word.class)
                    .setParameter("dictionaryName", dictionaryName)
                    .setParameter("word", word)
                    .getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }

    @Override
    public Word getRandomWordFromDictionaryByDictionaryName(String dictionaryName) {
        Word result = null;
        Criteria crit = getCurrentSession().createCriteria(Word.class);
        crit.setProjection(Projections.rowCount());
        int count = ((Number) crit.uniqueResult()).intValue();
        if (0 != count) {
            int index = new Random().nextInt(count);
            crit = getCurrentSession().createCriteria(Word.class);
            result = (Word) crit.setFirstResult(index).setMaxResults(1).uniqueResult();

        }
        return result;
    }
}
