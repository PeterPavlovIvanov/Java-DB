package demos.springdata.restdemo.dtos.firstEx;

import com.google.gson.annotations.Expose;

public class SellerDto {
    @Expose
    private String name;

    public SellerDto() {
    }

    public SellerDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
