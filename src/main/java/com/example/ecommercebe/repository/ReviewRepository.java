package com.example.ecommercebe.repository;

import com.example.ecommercebe.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
