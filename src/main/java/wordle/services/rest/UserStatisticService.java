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
            return user.getStatistic();
        }
        return null;
    }

    public List<UserStatistic> getUsersStatistic(int start, int end, String param, String orderBy) {
        return userStatisticDao.getUsersStatisticOrderByParam(start, end, param, orderBy);
    }

    public UserStatistic updateUserStatisticAsGameResult(int stepsCount, UserStatistic userStatistic, boolean isWin) {
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
        return userStatistic;
    }

    public Long getUsersStatisticsCount() {
        return userStatisticDao.getUsersStatisticsCount();
    }
}
