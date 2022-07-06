package wordle.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "attempt")
@NamedEntityGraph(name = "graph.AttemptSteps",
        attributeNodes = @NamedAttributeNode(value = "steps", subgraph = "subgraph.step"),
        subgraphs = {
                @NamedSubgraph(name = "subgraph.step",
                        attributeNodes = @NamedAttributeNode(value = "wordCharacters"))
        }
)
@NamedEntityGraph(name = "graph.Attempt")
@Schema
public class Attempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "user_id", nullable = false)
    private Long user;

    @Column(name = "coins_win", nullable = false)
    private Integer coinsWin;

    @Column(name = "is_admin_accrued", nullable = false)
    private Boolean isAdminAccrued = false;

    @Column(name = "is_win", nullable = false)
    private Boolean isWin = false;

    @OneToMany(mappedBy = "attempt", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Step> steps = new LinkedHashSet<>();

    public Attempt(LocalDate date, Long user, Integer coinsWin, Boolean isAdminAccrued, Boolean isWin) {
        this.date = date;
        this.user = user;
        this.coinsWin = coinsWin;
        this.isAdminAccrued = isAdminAccrued;
        this.isWin = isWin;
    }

    public Attempt() {

    }

    public Set<Step> getSteps() {
        return steps;
    }

    public void setSteps(Set<Step> steps) {
        this.steps = steps;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Integer getCoinsWin() {
        return coinsWin;
    }

    public void setCoinsWin(Integer coinsWin) {
        this.coinsWin = coinsWin;
    }

    public Boolean getIsAdminAccrued() {
        return isAdminAccrued;
    }

    public void setIsAdminAccrued(Boolean isAdminAccrued) {
        this.isAdminAccrued = isAdminAccrued;
    }

    public Boolean getIsWin() {
        return isWin;
    }

    public void setIsWin(Boolean isWin) {
        this.isWin = isWin;
    }

}