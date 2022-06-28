package wordle.services.dao;

import wordle.model.dto.Attempt;

import java.util.List;

public interface AttemptDao {

    void save(Attempt attempt);

    List<Attempt> getAllByUserId(Long userId, int start, int end);

    Attempt getLastAttemptByUserId(Long userId);

    Long getAttemptCountByUserId(Long userId);
}
