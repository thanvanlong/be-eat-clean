package com.tb.eatclean.repo;

import com.tb.eatclean.entity.promotion.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepo extends JpaRepository<Promotion, Long> {
    Promotion findByCode(String code);
}
