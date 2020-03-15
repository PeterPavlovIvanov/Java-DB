package demos.springdata.restdemo.entities;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private int age;
    private Set<Product> sold;
    private Set<Product> bought;
    private Set<User> friends;

    public User() {
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_friends",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="friend_id", referencedColumnName="id"))
    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    @OneToMany(mappedBy = "seller",fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    public Set<Product> getSold() {
        return sold;
    }

    public void setSold(Set<Product> stock) {
        this.sold = stock;
    }

    @OneToMany(mappedBy = "buyer",fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    public Set<Product> getBought() {
        return bought;
    }

    public void setBought(Set<Product> purchases) {
        this.bought = purchases;
    }

    @Column(name ="first_name",nullable = true)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name ="last_name",nullable = false)
    @Length(min = 3)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    @Column(name ="age",nullable = true)
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
