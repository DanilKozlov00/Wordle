package wordle.services.dao;

import wordle.model.dto.User;

import java.util.List;

public interface UserDao {

    User save(User user);

    User getByEmail(String email);

    boolean deleteByEmail(String email);

    void addCoinsToUserBalanceByEmail(User user, int coins);

    boolean updatePassword(String email, String newPassword);

    boolean updateEmail(String oldEmail, String newEmail);

    User getByName(String name);

    User getByNameAndEmail(String email, String name);

    Long getRatingPosition(String email);

    List<User> getTopBalanceUsers(Integer count);
}
