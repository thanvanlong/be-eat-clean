package com.tb.eatclean.repo;

import java.util.List;
import java.util.Optional;

import com.tb.eatclean.entity.carts.Cart;
import com.tb.eatclean.entity.carts.Status;
import com.tb.eatclean.entity.product.Product;
import com.tb.eatclean.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    Optional<Cart> findByFoods(Product food);
    List<Cart> findByUserAndStatus(User user, Status status);

    int countByUserAndStatus(User user, Status status);
}
