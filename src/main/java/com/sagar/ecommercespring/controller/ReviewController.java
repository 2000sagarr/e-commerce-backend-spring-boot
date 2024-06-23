package com.sagar.ecommercespring.controller;

import com.sagar.ecommercespring.exception.ProductException;
import com.sagar.ecommercespring.exception.UserException;
import com.sagar.ecommercespring.model.Review;
import com.sagar.ecommercespring.model.User;
import com.sagar.ecommercespring.request.ReviewRequest;
import com.sagar.ecommercespring.service.ReviewService;
import com.sagar.ecommercespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private ReviewService reviewService;
    private UserService userService;

    @Autowired
    public ReviewController(ReviewService reviewService,UserService userService) {
        this.reviewService=reviewService;
        this.userService=userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Review> createReviewHandler(@RequestBody ReviewRequest req, @RequestHeader("Authorization") String jwt)
            throws UserException, ProductException {
        User user=userService.findUserProfileByJwt(jwt);
        Review review=reviewService.createReview(req, user);
        return new ResponseEntity<Review>(review, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductsReviewHandler(@PathVariable Long productId){
        List<Review>reviews=reviewService.getAllReview(productId);
        return new ResponseEntity<List<Review>>(reviews,HttpStatus.OK);
    }
}
