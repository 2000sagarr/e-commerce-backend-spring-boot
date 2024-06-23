package com.sagar.ecommercespring.repository;

import com.sagar.ecommercespring.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<CartItemRepository, Long> {

    @Query("Select r from Rating r where r.product.id=:productId")
    public List<Review> getAllProductsReview(@Param("productId") Long productId);
}
