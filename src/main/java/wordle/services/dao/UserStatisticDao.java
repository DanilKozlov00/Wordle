package wordle.services.dao;

import wordle.model.dto.User;
import wordle.model.dto.UserStatistic;

import java.util.List;

public interface UserStatisticDao {
    UserStatistic getByUser(User user);

    List<UserStatistic> getUsersStatistic(int start, int end);

    Long getUsersStatisticsCount();

    void update(UserStatistic userStatistic);
}
