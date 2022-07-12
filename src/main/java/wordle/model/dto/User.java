package wordle.model.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Hidden
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone", nullable = false, length = 12)
    private String phone;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "nickname", length = 30)
    private String nickname;

    @Column(name = "balance", nullable = false)
    private Long balance = 0L;

    @Enumerated(EnumType.STRING)
    @Type(type = "wordle.model.dto.EnumTypePostgreSql")
    private Role role = Role.ROLE_user;

    @JsonIgnore
    @Hidden
    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "statistic_id", nullable = false)
    private UserStatistic statistic;

    @Column(name = "registered_date")
    private Instant registeredDate = Instant.now();

    public User() {
    }

    public Instant getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Instant registeredDate) {
        this.registeredDate = registeredDate;
    }

    public UserStatistic getStatistic() {
        return statistic;
    }

    public void setStatistic(UserStatistic statistic) {
        this.statistic = statistic;
    }

    @JsonGetter("password")
    public String getJSONPassword() {
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void addToBalance(int coins) {
        balance += coins;
    }
}