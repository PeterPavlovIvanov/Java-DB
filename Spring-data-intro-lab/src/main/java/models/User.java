package models;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "accounts", schema = "bank", catalog = "bank")
public class User extends BaseEntity {
    private String username;
    private int age;
    private Set<Account> accounts;

    public User(String username,int age) {
        this.username = username;
        this.age = age;
        this.accounts = new HashSet<>();
    }

    @Column(name = "username", unique = true, nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "age", nullable = false)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @OneToMany(mappedBy = "user", targetEntity = Account.class)
    @NonNull
    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
