package wordle.services.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wordle.model.dto.User;
import wordle.services.dao.UserDao;
import wordle.model.exceptions.UserException;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User saveUser(User user) {
        return userDao.save(user);
    }

    public User authorizeUser(String email, String password) throws UserException {
        User user = userDao.getByEmail(email);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return user;
            }
            throw new UserException(UserException.INCORRECT_PASSWORD);
        }
        throw new UserException(UserException.USER_NOT_FOUND);
    }

    public boolean deleteUserByEmail(String email) {
        return userDao.deleteByEmail(email);
    }

}
