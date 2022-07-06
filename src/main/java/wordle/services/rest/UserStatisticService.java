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

    public void updateUserStatistic(int stepsCount, User user, boolean isWin) {
        UserStatistic userStatistic = user.getStatistic();
        userStatistic.incrementGames();
        if (isWin) {
            userStatistic.incrementWins();
            switch (stepsCount) {
                case 1:
                    userStatistic.incrementFirstWins();
                    break;
                case 2:
                    userStatistic.incrementSecondWins();
                    break;
                case 3:
                    userStatistic.incrementThirdWins();
                    break;
                case 4:
                    userStatistic.incrementFourWins();
                    break;
                case 5:
                    userStatistic.incrementFiveWins();
                    break;
                case 6:
                    userStatistic.incrementSixWins();
                    break;
            }
        }
        userStatisticDao.update(userStatistic);
    }

    public Long getUsersStatisticsCount() {
        return userStatisticDao.getUsersStatisticsCount();
    }
}
