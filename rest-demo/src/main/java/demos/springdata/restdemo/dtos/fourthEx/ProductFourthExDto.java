package demos.springdata.restdemo.dtos.fourthEx;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class ProductFourthExDto {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;

    public ProductFourthExDto() {
    }

    public ProductFourthExDto(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
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
