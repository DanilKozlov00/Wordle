package wordle.services.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wordle.model.dto.Attempt;
import wordle.model.dto.User;
import wordle.services.dao.AttemptDao;
import wordle.services.dao.UserDao;

import java.util.List;

@Service
public class AttemptService {

    @Autowired
    UserDao userDao;
    @Autowired
    AttemptDao attemptDao;

    public List<Attempt> getUserAttemptByUserIdOrderByParam(String email, int start, int end, String param, String orderBy) {
        User user = userDao.getByEmail(email);

        if (user != null) {
            return attemptDao.getUserAttemptByUserIdOrderByParam(user.getId(), start, end, param, orderBy);
        }
        return null;
    }

    public Attempt getLastAttemptByUserEmail(String email) {
        User user = userDao.getByEmail(email);

        if (user != null) {
            return attemptDao.getLastAttemptByUserId(user.getId());
        }
        return null;
    }

    public Long getAttemptCountByUserEmail(String email) {
        User user = userDao.getByEmail(email);

        if (user != null) {
            return attemptDao.getAttemptCountByUserId(user.getId());
        }
        return 0L;
    }


}
