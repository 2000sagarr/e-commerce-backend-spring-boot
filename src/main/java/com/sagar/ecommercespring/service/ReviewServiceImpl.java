package com.sagar.ecommercespring.service;

import com.sagar.ecommercespring.exception.ProductException;
import com.sagar.ecommercespring.model.Product;
import com.sagar.ecommercespring.model.Review;
import com.sagar.ecommercespring.model.User;
import com.sagar.ecommercespring.repository.ProductRespository;
import com.sagar.ecommercespring.repository.ReviewRepository;
import com.sagar.ecommercespring.request.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final ProductService productService;
    private final ProductRespository productRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, ProductService productService, ProductRespository productRepository) {
        this.reviewRepository=reviewRepository;
        this.productService=productService;
        this.productRepository=productRepository;
    }

    @Override
    public Review createReview(ReviewRequest req,User user) throws ProductException {
        Product product=productService.findProductById(req.getProductId());
        Review review=new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReview(req.getReview());
        review.setCreatedAt(LocalDateTime.now());

        productRepository.save(product);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }

}
