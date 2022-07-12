package wordle.services.dao.impl;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wordle.model.dto.Attempt;
import wordle.model.dto.Step;
import wordle.model.dto.User;
import wordle.model.dto.UserStatistic;
import wordle.model.dto.WordCharacter;
import wordle.services.dao.DaoSessionFactory;
import wordle.services.rest.UserStatisticService;


@Repository
@Transactional
public class GameDao extends DaoSessionFactory {

    @Autowired
    UserStatisticService userStatisticService;

    public boolean saveGameResult(Attempt attempt, User user) {
        UserStatistic userStatistic = user.getStatistic();
        int stepsCount = attempt.getSteps().size();
        user.setStatistic(userStatisticService.updateUserStatisticAsGameResult(stepsCount, userStatistic, attempt.getIsWin()));
        int coinsWin = attempt.getCoinsWin();
        if (coinsWin > 0) {
            user.addToBalance(coinsWin);
        }
        setAttemptWithSteps(attempt);
        try (Session session = openSession()) {
            session.beginTransaction();
            session.update(user);
            session.save(attempt);
            session.flush();
            session.getTransaction().commit();
        } catch (HibernateException hibernateException) {
            return false;
        }
        return true;
    }

    private void setAttemptWithSteps(Attempt attempt) {
        for (Step step : attempt.getSteps()) {
            step.setAttempt(attempt);
            setStepsWithWordCharacters(step);
        }
    }

    private void setStepsWithWordCharacters(Step step) {
        for (WordCharacter wordCharacter : step.getWordCharacters()) {
            wordCharacter.setStep(step);
        }
    }

}
