package wordle.model.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_statistic")
@NamedEntityGraph(name = "graph.UsersStatistics",
        attributeNodes = @NamedAttributeNode(value = "user"))
public class UserStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "games_count", nullable = false)
    private Long gamesCount = 0L;

    @Column(name = "wins", nullable = false)
    private Long wins = 0L;

    @Column(name = "first_win", nullable = false)
    private Integer firstWin = 0;

    @Column(name = "second_win", nullable = false)
    private Integer secondWin = 0;

    @Column(name = "third_win", nullable = false)
    private Integer thirdWin = 0;

    @Column(name = "four_win", nullable = false)
    private Integer fourWin = 0;

    @Column(name = "five_win", nullable = false)
    private Integer fiveWin = 0;

    @Column(name = "six_win", nullable = false)
    private Integer sixWin = 0;

    @OneToOne(mappedBy = "statistic")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGamesCount() {
        return gamesCount;
    }

    public void setGamesCount(Long gamesCount) {
        this.gamesCount = gamesCount;
    }

    public Long getWins() {
        return wins;
    }

    public void setWins(Long wins) {
        this.wins = wins;
    }

    public Integer getFirstWin() {
        return firstWin;
    }

    public void setFirstWin(Integer firstWin) {
        this.firstWin = firstWin;
    }

    public Integer getSecondWin() {
        return secondWin;
    }

    public void setSecondWin(Integer secondWin) {
        this.secondWin = secondWin;
    }

    public Integer getThirdWin() {
        return thirdWin;
    }

    public void setThirdWin(Integer thirdWin) {
        this.thirdWin = thirdWin;
    }

    public Integer getFourWin() {
        return fourWin;
    }

    public void setFourWin(Integer fourWin) {
        this.fourWin = fourWin;
    }

    public Integer getFiveWin() {
        return fiveWin;
    }

    public void setFiveWin(Integer fiveWin) {
        this.fiveWin = fiveWin;
    }

    public Integer getSixWin() {
        return sixWin;
    }

    public void setSixWin(Integer sixWin) {
        this.sixWin = sixWin;
    }

    public void incrementWins() {
        this.wins++;
    }

    public void incrementGames() {
        this.gamesCount++;
    }

    public void incrementFirstWins() {
        this.firstWin++;
    }

    public void incrementSecondWins() {
        this.secondWin++;
    }

    public void incrementThirdWins() {
        this.thirdWin++;
    }

    public void incrementFourWins() {
        this.fourWin++;
    }

    public void incrementFiveWins() {
        this.fiveWin++;
    }

    public void incrementSixWins() {
        this.sixWin++;
    }

}