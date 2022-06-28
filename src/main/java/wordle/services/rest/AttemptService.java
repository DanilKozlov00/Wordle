package wordle.services.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wordle.model.dto.WordCharacter;
import wordle.model.dto.Attempt;
import wordle.model.dto.Step;
import wordle.model.dto.User;
import wordle.services.dao.AttemptDao;
import wordle.services.dao.UserDao;

import java.util.HashSet;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class AttemptService {

    @Autowired
    UserDao userDao;
    @Autowired
    AttemptDao attemptDao;

    public void save(List<List<WordCharacter>> wordCharacters, String email, Integer coins) {
        User user = userDao.getByEmail(email);

        if (user != null) {
            Attempt attempt = new Attempt(java.time.LocalDate.now(), user.getId(), coins, false, coins > 0);
            Set<Step> steps = new HashSet<>();
            int count = 0;
            for (List<WordCharacter> wordCharacter : wordCharacters) {
                Step step = new Step();
                step.setWordCharacters(setStepInWordCharacters(wordCharacter, step));
                step.setNumber(count++);
                steps.add(step);
            }
            attempt.setSteps(steps);
            attemptDao.save(attempt);
            if (coins > 0) {
                userDao.addCoinsToUserBalanceByEmail(email, coins);
            }
        }
    }

    public List<Attempt> getAllByUserEmail(String email, int start, int end) {
        User user = userDao.getByEmail(email);

        if (user != null) {
            return attemptDao.getAllByUserId(user.getId(), start, end);
        }
        return null;
    }

    public Attempt getLastAttemptByUserEmail(String email) {
        User user = userDao.getByEmail(email);

        if (user != null) {
            return attemptDao.getLastAttemptByUserId(user.getId());
        }
        return null;
    }

    public Long getAttemptCountByUserEmail(String email) {
        User user = userDao.getByEmail(email);

        if (user != null) {
            return attemptDao.getAttemptCountByUserId(user.getId());
        }
        return 0L;
    }

    private List<WordCharacter> setStepInWordCharacters(List<WordCharacter> wordCharacters, Step step) {
        List<WordCharacter> result = new LinkedList<>();
        for (WordCharacter wordCharacter : wordCharacters) {
            wordCharacter.setStep(step);
            result.add(wordCharacter);
        }
        return result;
    }


}
