package wordle.services.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wordle.model.dto.Attempt;
import wordle.model.dto.User;
import wordle.services.dao.AttemptDao;
import wordle.services.dao.UserDao;
import wordle.services.dao.impl.GameDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserRatingService {

    @Autowired
    UserDao userDao;

    @Autowired
    GameDao gameDao;


    @Value("#{${coinsAsRating}}")
    Map<Integer, Integer> coinsAsRatingPosition = new HashMap();

    public boolean addCoinsAsEndMouth() {
        List<User> topUserList = userDao.getTopBalanceUsers(coinsAsRatingPosition.size());

        for (int i = 0; i < topUserList.size(); i++) {
            boolean isWin = false;
            boolean isAdminAccrued = true;
            User user = topUserList.get(i);
            int coins = coinsAsRatingPosition.get(i + 1);
            Attempt attempt = new Attempt(java.time.LocalDate.now(), user.getId(), coins, isAdminAccrued, isWin);
            if (!gameDao.saveGameResult(attempt, user)) {
                return false;
            }
        }
        return true;
    }


}
