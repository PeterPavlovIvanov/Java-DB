package models;

import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts",schema = "bank",catalog = "bank")
public class Account extends BaseEntity{
    private BigDecimal balance;
    private User user;

    public Account(User user, BigDecimal balance) {
        this.user = user;
        this.balance= balance;
    }

    @Column(name = "balance",nullable = false)
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @ManyToOne
    @NonNull
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
