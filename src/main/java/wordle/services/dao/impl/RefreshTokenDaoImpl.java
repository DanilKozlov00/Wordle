package wordle.services.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wordle.model.dto.RefreshToken;
import wordle.model.dto.User;
import wordle.services.dao.DaoSessionFactory;
import wordle.services.dao.RefreshTokenDao;

import javax.persistence.NoResultException;
import java.io.Serializable;

@Repository
@Transactional
public class RefreshTokenDaoImpl extends DaoSessionFactory implements RefreshTokenDao {

    @Override
    public RefreshToken findByToken(String token) {
        Session session = getCurrentSession();
        try {
            return session.createQuery("from RefreshToken where token=:token", RefreshToken.class).setParameter("token", token).getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        try (Session session = openSession()) {
            session.beginTransaction();
            Serializable serializable = session.save(refreshToken);
            session.flush();
            session.getTransaction().commit();
            return session.get(RefreshToken.class, serializable);
        } catch (HibernateException hibernateException) {
            return null;
        }
    }

    @Override
    public boolean delete(RefreshToken refreshToken) {
        try (Session session = openSession()) {
            session.beginTransaction();
            session.delete(refreshToken);
            session.flush();
            session.getTransaction().commit();
            return true;
        } catch (HibernateException hibernateException) {
            return false;
        }
    }

    @Override
    public RefreshToken findByUserEmail(String email) {
        Session session = getCurrentSession();
        try {
            return session.createQuery("from RefreshToken where user.email=:email", RefreshToken.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException noResultException) {
            return null;
        }
    }

    @Override
    public void deleteByUser(User user) {
        Session session = getCurrentSession();
        session.createQuery("delete from RefreshToken where user=:user").setParameter("user", user).executeUpdate();
    }
}
