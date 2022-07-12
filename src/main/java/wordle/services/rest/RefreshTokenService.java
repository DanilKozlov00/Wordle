package wordle.services.rest;

import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wordle.model.dto.RefreshToken;
import wordle.model.dto.User;
import wordle.services.dao.RefreshTokenDao;
import wordle.services.dao.UserDao;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${jwt.refreshExpirationSec}")
    private Long refreshTokenDurationSec;

    @Autowired
    private RefreshTokenDao refreshTokenDao;

    @Autowired
    private UserDao userDao;

    public RefreshToken findByToken(String token) {
        return refreshTokenDao.findByToken(token);
    }

    public RefreshToken createRefreshToken(String email) {
        RefreshToken oldToken = refreshTokenDao.findByUserEmail(email);
        if (oldToken != null) {
            refreshTokenDao.delete(oldToken);
        }

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userDao.getByEmail(email));
        refreshToken.setExpiryDate(Instant.now().plusSeconds(refreshTokenDurationSec));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenDao.save(refreshToken);
        return refreshToken;
    }

    public void verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenDao.delete(token);
            throw new JwtException("Refresh token was expired. Please make a new signin request");
        }
    }

    public void deleteByUser(User user) {
        refreshTokenDao.deleteByUser(user);
    }
}
