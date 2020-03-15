package demos.springdata.restdemo.dtos.fourthEx;

import com.google.gson.annotations.Expose;


public class UserFourthExDto {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int age;
    @Expose
    private SoldProductsDto soldProducts;

    public UserFourthExDto() {
    }

    public UserFourthExDto(String firstName, String lastName, int age, SoldProductsDto productFourthExDtos) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.soldProducts = productFourthExDtos;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public SoldProductsDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(SoldProductsDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}
