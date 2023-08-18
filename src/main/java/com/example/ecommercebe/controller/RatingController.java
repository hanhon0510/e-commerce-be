package com.example.ecommercebe.controller;


import com.example.ecommercebe.exception.ProductException;
import com.example.ecommercebe.exception.UserException;
import com.example.ecommercebe.model.Rating;
import com.example.ecommercebe.model.User;
import com.example.ecommercebe.request.RatingRequest;
import com.example.ecommercebe.service.RatingService;
import com.example.ecommercebe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req,
                                               @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);

        Rating rating = ratingService.createRating(req, user);


        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductRatings(@PathVariable("productId") Long productId,
                                                          @RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);

        List<Rating> ratings = ratingService.getProductRatings(productId);



        return new ResponseEntity<>(ratings, HttpStatus.ACCEPTED);
    }
}
