package wordle.services.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wordle.model.dto.User;
import wordle.model.dto.UserStatistic;
import wordle.services.dao.UserDao;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User saveUser(User user) {
        UserStatistic userStatistic = new UserStatistic();
        userStatistic.setUser(user);
        user.setStatistic(userStatistic);
        return userDao.save(user);
    }

    public boolean deleteUserByEmail(String email) {
        return userDao.deleteByEmail(email);
    }

    public boolean updateEmail(String oldEmail, String newEmail) {
        return userDao.updateEmail(oldEmail, newEmail);
    }

    public boolean updatePassword(String email, String newPassword) {
        return userDao.updatePassword(email, newPassword);
    }

    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    public User getByName(String name) {
        return userDao.getByName(name);
    }

    public User getByNameAndEmail(String name, String email) {
        return userDao.getByNameAndEmail(email, name);
    }

    public Long getUserRatingPosition(String email) {
        return userDao.getRatingPosition(email);
    }
}
