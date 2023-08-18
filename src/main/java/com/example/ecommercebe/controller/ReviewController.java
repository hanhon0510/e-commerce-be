package com.example.ecommercebe.controller;


import com.example.ecommercebe.exception.ProductException;
import com.example.ecommercebe.exception.UserException;
import com.example.ecommercebe.model.Rating;
import com.example.ecommercebe.model.Review;
import com.example.ecommercebe.model.User;
import com.example.ecommercebe.request.RatingRequest;
import com.example.ecommercebe.request.ReviewRequest;
import com.example.ecommercebe.service.RatingService;
import com.example.ecommercebe.service.ReviewService;
import com.example.ecommercebe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<Review> createRating(@RequestBody ReviewRequest req,
                                               @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);

        Review review = reviewService.createReview(req, user);


        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductRatings(@PathVariable("productId") Long productId,
                                                          @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);

        List<Review> reviews = reviewService.getAllReviews(productId);



        return new ResponseEntity<>(reviews, HttpStatus.ACCEPTED);
    }
}
