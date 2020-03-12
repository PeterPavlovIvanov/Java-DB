package com.example.demo.dtos;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class GameAddDto {
    private String title;
    private BigDecimal price;
    private Double size;
    private String trailer;
    private String image;
    private String description;
    private LocalDate releaseDate;

    public GameAddDto() {
    }

    public GameAddDto(String title, BigDecimal price, Double size, String trailer, String image, String description, LocalDate releaseDate) {
        this.title = title;
        this.price = price;
        this.size = size;
        this.trailer = trailer;
        this.image = image;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    @Pattern(regexp ="^[A-Z]+[A-Za-z]+$",message = "Invalid title.")
    @Size(min= 3,max = 100,message = "Invalid title length.")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DecimalMin(value = "0",message = "Price must not be a negative number.")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Min(value =0,message = "Size must not be a negative number.")
    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    @Pattern(regexp = "(.+){11}",message = "Trailer URL must be 11 symbols")
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Pattern(regexp = "^http:\\/\\/.+|https.+",message = "Thumbnail start is invalid.")
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Pattern(regexp ="(.+){11}",message = "Description must be at least 20 symbols.")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
