package demos.springdata.restdemo.dtos.fourthEx;

import com.google.gson.annotations.Expose;

import java.util.Set;

public class SoldProductsDto {
    @Expose
    private int count;
    @Expose
    private Set<ProductFourthExDto> products;

    public SoldProductsDto() {
    }

    public SoldProductsDto(int count, Set<ProductFourthExDto> products) {
        this.count = count;
        this.products = products;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Set<ProductFourthExDto> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductFourthExDto> products) {
        this.products = products;
    }
}
