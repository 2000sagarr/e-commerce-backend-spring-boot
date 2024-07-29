package com.sagar.ecommercespring.request;

import com.sagar.ecommercespring.model.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class CreateProductRequest {
    private String title;

    private String description;

    private int price;

    private int discountedPrice;

    private int discountPersent;

    private int quantity;

    private String brand;

    private String color;

    private Set<Size> size=new HashSet<>();

    private String imageUrl;

    private String topLavelCategory;

    private String secondLavelCategory;

    private String thirdLavelCategory;

    @Override
    public String toString() {
        return "CreateProductRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", discountedPrice=" + discountedPrice +
                ", discountPersent=" + discountPersent +
                ", quantity=" + quantity +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", size=" + size +
                ", imageUrl='" + imageUrl + '\'' +
                ", topLavelCategory='" + topLavelCategory + '\'' +
                ", secondLavelCategory='" + secondLavelCategory + '\'' +
                ", thirdLavelCategory='" + thirdLavelCategory + '\'' +
                '}';
    }
}
