package demos.springdata.restdemo.entities;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;

    private BigDecimal price;

    private User buyer;

    private User seller;

    private Set<Category> categories;

    public Product() {
    }

    @ManyToMany(cascade = CascadeType.ALL)
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Column(name = "name", nullable = false)
    @Length(min = 3)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    public User getBuyer() {
        return this.buyer;
    }

    public void setBuyer(User buyerId) {
        this.buyer = buyerId;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
   // @NotNull(message = "seller must not be null.")
    public User getSeller() {
        return this.seller;
    }

    public void setSeller(User sellerId) {
        this.seller = sellerId;
    }
}
