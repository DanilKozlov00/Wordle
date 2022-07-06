package wordle.services.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wordle.model.dto.User;
import wordle.model.dto.UserStatistic;
import wordle.services.dao.DaoSessionFactory;
import wordle.services.dao.UserStatisticDao;

import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class UserStatisticDaoImpl extends DaoSessionFactory implements UserStatisticDao {

    @Override
    public UserStatistic getByUser(User user) {
        Session session = getCurrentSession();
        try {
            return session.createQuery("from UserStatistic as u where u.user=:user", UserStatistic.class).setParameter("user", user).getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }

    @Override
    public List<UserStatistic> getUsersStatistic(int start, int end) {
        Session session = getCurrentSession();
        EntityGraph<?> graph = session.getEntityGraph("graph.UsersStatistics");
        TypedQuery<UserStatistic> q = session.createQuery("from UserStatistic order by user.balance desc", UserStatistic.class)
                .setFirstResult(start)
                .setMaxResults(end);
        q.setHint("javax.persistence.fetchgraph", graph);
        return q.getResultList();
    }

    @Override
    public Long getUsersStatisticsCount() {
        Session session = getCurrentSession();
        TypedQuery<Long> q = session.createQuery("select  count (*) from UserStatistic ", Long.class);
        return q.getSingleResult();
    }

    @Override
    public void update(UserStatistic userStatistic) {
        Session session = getCurrentSession();
        session.update(userStatistic);
    }


}
