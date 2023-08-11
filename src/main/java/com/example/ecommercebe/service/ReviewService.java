package com.example.ecommercebe.service;

import com.example.ecommercebe.exception.ProductException;
import com.example.ecommercebe.model.Review;
import com.example.ecommercebe.model.User;
import com.example.ecommercebe.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    public Review createReview(ReviewRequest req, User user) throws ProductException;

    public List<Review> getAllReviews(Long productId);
}
