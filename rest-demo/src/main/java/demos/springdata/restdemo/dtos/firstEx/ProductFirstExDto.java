package demos.springdata.restdemo.dtos.firstEx;

import com.google.gson.annotations.Expose;
import demos.springdata.restdemo.entities.User;

import java.math.BigDecimal;

public class ProductFirstExDto {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;
    @Expose
    private User seller;

    public ProductFirstExDto() {
    }

    public ProductFirstExDto(String name, BigDecimal price, User seller) {
        this.name = name;
        this.price = price;
        this.seller = seller;
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

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }
}
