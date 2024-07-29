package com.sagar.ecommercespring.service;

import com.sagar.ecommercespring.exception.ProductException;
import com.sagar.ecommercespring.model.Product;
import com.sagar.ecommercespring.model.Rating;
import com.sagar.ecommercespring.model.User;
import com.sagar.ecommercespring.repository.RatingRepository;
import com.sagar.ecommercespring.request.RatingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService{
    private final RatingRepository ratingRepository;
    private final ProductService productService;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository,ProductService productService) {
        this.ratingRepository=ratingRepository;
        this.productService=productService;
    }

    @Override
    public Rating createRating(RatingRequest req,User user) throws ProductException {

        Product product=productService.findProductById(req.getProductId());

        Rating rating=new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        return ratingRepository.getAllProductsRating(productId);
    }
}
