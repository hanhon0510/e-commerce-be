package com.example.ecommercebe.repository;

import com.example.ecommercebe.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c from Cart c WHERE c.user.id = :userId")
    public Cart findUserById(@Param("userId") Long userId);
}
