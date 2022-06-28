package wordle.services.dao.impl;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wordle.model.dto.User;
import wordle.services.dao.DaoSessionFactory;
import wordle.services.dao.UserDao;

import javax.persistence.NoResultException;
import java.io.Serializable;

@Repository
@Transactional
public class UserDaoImpl extends DaoSessionFactory implements UserDao {

    @Override
    public User save(User user) {
        Session session = getCurrentSession();
        if (getByEmail(user.getEmail()) == null) {
            Serializable serializable = session.save(user);
            session.flush();
            return session.get(User.class, serializable);
        } else {
            return null;
        }
    }

    @Override
    public User getByEmail(String email) {
        Session session = getCurrentSession();
        try {
            return session.createQuery("from User as u where u.email=:email", User.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }

    @Override
    public boolean deleteByEmail(String email) {
        Session session = getCurrentSession();
        User user = getByEmail(email);

        if (user != null) {
            session.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public void addCoinsToUserBalanceByEmail(String email, int coins) {
        Session session = getCurrentSession();
        User user = getByEmail(email);
        if (user != null) {
            user.setBalance(user.getBalance() + coins);
            session.update(user);

        }
    }

}
