package wordle.services.dao;

import wordle.model.dto.Attempt;

import java.util.List;

public interface AttemptDao {

    List<Attempt> getUserAttemptByUserIdOrderByParam(Long userId, int start, int end, String orderByParam, String orderBy);

    Attempt getLastAttemptByUserId(Long userId);

    Long getAttemptCountByUserId(Long userId);
}
