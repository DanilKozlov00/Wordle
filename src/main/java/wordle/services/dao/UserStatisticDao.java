package wordle.services.dao;

import wordle.model.dto.UserStatistic;

import java.util.List;

public interface UserStatisticDao {

    List<UserStatistic> getUsersStatisticOrderByParam(int start, int end, String orderByParam, String orderBy);

    Long getUsersStatisticsCount();

}
