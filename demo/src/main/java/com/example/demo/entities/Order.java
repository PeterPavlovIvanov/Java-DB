package com.example.demo.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private long buyer_id;
    private Set<Game> games;

    public Order() {
    }

    @ManyToMany
    @JoinTable(name = "ordered_games_id",
            joinColumns = @JoinColumn(name="order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="ordered_game_id", referencedColumnName="id"))
    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public long getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(long buyer_id) {
        this.buyer_id = buyer_id;
    }
}
