package demos.springdata.restdemo.dtos;

import com.google.gson.annotations.Expose;
import demos.springdata.restdemo.entities.Category;
import demos.springdata.restdemo.entities.User;

import java.math.BigDecimal;
import java.util.Set;

public class ProductSeedDto {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;

    private User buyer;

    private User seller;

    private Set<Category> categories;

    public ProductSeedDto() {
    }

    public ProductSeedDto(String name, BigDecimal price, User buyer, User seller, Set<Category> categories) {
        this.name = name;
        this.price = price;
        this.buyer = buyer;
        this.seller = seller;
        this.categories = categories;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
