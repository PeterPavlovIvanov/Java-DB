package demos.springdata.restdemo.dtos.secondEx;

import com.google.gson.annotations.Expose;

import java.util.Set;

public class UserSecondExDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Set<ProductSecondExDto> sold;

    public UserSecondExDto() {
    }

    public UserSecondExDto(String firstName, String lastName, Set<ProductSecondExDto> sold) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.sold = sold;
    }

    public Set<ProductSecondExDto> getSold() {
        return sold;
    }

    public void setSold(Set<ProductSecondExDto> sold) {
        this.sold = sold;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
