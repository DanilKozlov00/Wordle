package wordle.services.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wordle.model.dto.Attempt;
import wordle.services.dao.AttemptDao;
import wordle.services.dao.DaoSessionFactory;

import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
@Transactional
public class AttemptDaoImpl extends DaoSessionFactory implements AttemptDao {
    @Override
    public void save(Attempt attempt) {
        Session session = getCurrentSession();
        session.save(attempt);
    }

    @Override
    public List<Attempt> getAllByUserId(Long userId, int start, int end) {
        Session session = getCurrentSession();
        EntityGraph<?> graph = session.getEntityGraph("graph.AttemptSteps");
        TypedQuery<Attempt> q = session.createQuery("from Attempt as a where a.user=:userId", Attempt.class)
                .setParameter("userId", userId)
                .setFirstResult(start)
                .setMaxResults(end);
        q.setHint("javax.persistence.fetchgraph", graph);
        return q.getResultList();
    }

    @Override
    public Attempt getLastAttemptByUserId(Long userId) {
        Session session = getCurrentSession();
        try {
            EntityGraph<?> graph = session.getEntityGraph("graph.AttemptSteps");
            TypedQuery<Attempt> q = session.createQuery("from Attempt as a where a.user=:userId order by date desc", Attempt.class)
                    .setParameter("userId", userId).setMaxResults(1);
            q.setHint("javax.persistence.fetchgraph", graph);
            return q.getSingleResult();
        } catch (NoResultException resultException) {
            return null;
        }
    }

    @Override
    public Long getAttemptCountByUserId(Long userId) {
        Session session = getCurrentSession();
        TypedQuery<Long> q = session.createQuery("select  count (*) from Attempt as a where a.user=:userId", Long.class)
                .setParameter("userId", userId);
        return q.getSingleResult();
    }
}
