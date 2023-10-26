package com.tb.eatclean.repo;

import java.util.List;

import com.tb.eatclean.entity.carts.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
  List<Cart> findAllByUserIdAndStatus(Long userId, int status);

  Cart findByUserIdAndBookIdAndStatus(Long userId, Long bookId, int status);
}
