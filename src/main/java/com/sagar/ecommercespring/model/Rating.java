package com.sagar.ecommercespring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;



@Entity
@Table(name = "rating")
@Getter
@Setter
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "rating")
    private double rating;

    private LocalDateTime createdAt;

    public Rating() {
    }

    public Rating( User user, Product product, double rating, LocalDateTime createdAt) {
        this.user = user;
        this.product = product;
        this.rating = rating;
        this.createdAt = createdAt;
    }


}
