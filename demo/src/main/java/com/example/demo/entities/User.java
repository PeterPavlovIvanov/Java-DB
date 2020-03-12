package com.example.demo.entities;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String email;
    private String fullName;
    private String password;
    private Role role;
    private List<Game> games;
    private Set<Order> orders;

    public User() {
    }

    @OneToMany
    @JoinColumn(name = "buyer_id",referencedColumnName = "id")
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_games",
            joinColumns = @JoinColumn(name="games_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="user_id", referencedColumnName="id"))
    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    @Column(name = "email",unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "full_name")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Enumerated(EnumType.STRING)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
