package com.tb.eatclean.repo;

import com.tb.eatclean.entity.foods.Foods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodsRepo extends JpaRepository<Foods, Long> {
    Page<Foods> findAllByNameContaining(String search, Pageable pageable);
}
