package wordle.services.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wordle.model.dto.User;
import wordle.services.dao.DaoSessionFactory;
import wordle.services.dao.UserDao;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl extends DaoSessionFactory implements UserDao {

    @Autowired
    @Lazy
    PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        try (Session session = openSession()) {
            session.beginTransaction();
            Serializable serializable = session.save(user);
            session.flush();
            session.getTransaction().commit();
            return session.get(User.class, serializable);
        } catch (HibernateException hibernateException) {
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
        User user = getByEmail(email);
        if (user != null) {
            try (Session session = openSession()) {
                session.beginTransaction();
                session.delete(user);
                session.flush();
                session.getTransaction().commit();
                return true;
            } catch (HibernateException hibernateException) {
                return false;
            }
        }
        return false;
    }

    @Override
    public void addCoinsToUserBalanceByEmail(User user, int coins) {
        Session session = getCurrentSession();
        user.setBalance(user.getBalance() + coins);
        session.update(user);
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        Session session = getCurrentSession();
        User user = getByEmail(email);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            session.beginTransaction();
            session.update(user);
            session.flush();
            session.getTransaction().commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean updateEmail(String oldEmail, String newEmail) {
        Session session = getCurrentSession();
        User user = getByEmail(oldEmail);
        if (user != null) {
            user.setEmail(newEmail);
            session.beginTransaction();
            session.update(user);
            session.flush();
            session.getTransaction().commit();
            return true;
        }
        return false;
    }

    @Override
    public User getByName(String name) {
        Session session = getCurrentSession();
        try {
            return session.createQuery("from User as u where u.name=:name", User.class).setParameter("name", name).getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }

    @Override
    public User getByNameAndEmail(String email, String name) {
        Session session = getCurrentSession();
        try {
            return session.createQuery("from User as u where u.name=:name and u.email=:email", User.class).setParameter("name", name).setParameter("email", email).getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }

    @Override
    public Long getRatingPosition(String email) {
        Session session = getCurrentSession();
        TypedQuery<Long> typedQuery = session.createSQLQuery("SELECT row_number() over (order by balance) num from public.user where email=:email").setParameter("email", email).addScalar("num", LongType.INSTANCE);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException noResultException) {
            return -1L;
        }
    }

    @Override
    public List<User> getTopBalanceUsers(Integer count) {
        Session session = getCurrentSession();
        TypedQuery<User> q = session.createQuery("from User as u order by balance desc", User.class).setMaxResults(count);
        return q.getResultList();
    }
}
