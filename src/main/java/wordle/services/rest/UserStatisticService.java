package wordle.services.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wordle.model.dto.User;
import wordle.model.dto.UserStatistic;
import wordle.services.dao.UserDao;
import wordle.services.dao.UserStatisticDao;

import java.util.List;

@Service
public class UserStatisticService {

    @Autowired
    UserDao userDao;

    @Autowired
    UserStatisticDao userStatisticDao;

    public UserStatistic getUserStatistic(String email) {
        User user = userDao.getByEmail(email);
        if (user != null) {
            return userStatisticDao.getByUser(user);
        }
        return null;
    }

    public List<UserStatistic> getUsersStatistic(int start, int end) {
        return userStatisticDao.getUsersStatistic(start, end);
    }

    public Long getUsersStatisticsCount() {
        return userStatisticDao.getUsersStatisticsCount();
    }
}
