package wordle.services.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wordle.model.dto.UserStatistic;
import wordle.services.dao.DaoSessionFactory;
import wordle.services.dao.UserStatisticDao;

import javax.persistence.EntityGraph;

import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class UserStatisticDaoImpl extends DaoSessionFactory implements UserStatisticDao {

    @Override
    public List<UserStatistic> getUsersStatisticOrderByParam(int start, int end, String orderByParam, String orderBy) {
        Session session = getCurrentSession();
        EntityGraph<?> graph = session.getEntityGraph("graph.UsersStatistics");
        TypedQuery<UserStatistic> q = session.createQuery("from UserStatistic order by user." + orderByParam + " " + orderBy, UserStatistic.class)
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

}
