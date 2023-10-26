package com.tb.eatclean.repo;

import com.tb.eatclean.entity.product.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepo extends JpaRepository<Food, Long> {
    Page<Food> findAllByNameContaining(String search, Pageable pageable);
}
