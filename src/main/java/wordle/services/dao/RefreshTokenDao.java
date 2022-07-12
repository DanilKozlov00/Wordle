package wordle.services.dao;

import wordle.model.dto.RefreshToken;
import wordle.model.dto.User;

public interface RefreshTokenDao {

    RefreshToken findByToken(String token);

    RefreshToken save(RefreshToken refreshToken);

    boolean delete(RefreshToken refreshToken);

    RefreshToken findByUserEmail(String email);

    void deleteByUser(User user);
}
