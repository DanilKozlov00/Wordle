package wordle.services.dao;

import wordle.model.dto.User;

public interface UserDao {

    User save(User user);

    User getByEmail(String email);

    boolean deleteByEmail(String email);

    void addCoinsToUserBalanceByEmail(String email, int coins);
}
