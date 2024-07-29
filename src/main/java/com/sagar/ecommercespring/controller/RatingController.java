package com.sagar.ecommercespring.controller;

import com.sagar.ecommercespring.exception.ProductException;
import com.sagar.ecommercespring.exception.UserException;
import com.sagar.ecommercespring.model.Rating;
import com.sagar.ecommercespring.model.User;
import com.sagar.ecommercespring.request.RatingRequest;
import com.sagar.ecommercespring.service.RatingService;
import com.sagar.ecommercespring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    private final UserService userService;
    private final RatingService ratingServices;

    @Autowired
    public RatingController(UserService userService,RatingService ratingServices) {
        this.ratingServices=ratingServices;
        this.userService=userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Rating> createRatingHandler(@RequestBody RatingRequest req, @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user=userService.findUserProfileByJwt(jwt);
        Rating rating=ratingServices.createRating(req, user);
        return new ResponseEntity<>(rating, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductsReviewHandler(@PathVariable Long productId){

        List<Rating> ratings=ratingServices.getProductsRating(productId);
        return new ResponseEntity<>(ratings,HttpStatus.OK);
    }
}
