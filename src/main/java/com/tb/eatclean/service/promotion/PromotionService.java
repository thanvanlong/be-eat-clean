package com.tb.eatclean.service.promotion;

import com.tb.eatclean.entity.promotion.Promotion;

public interface PromotionService {
    Promotion getByCode(String code);
    void save(Promotion promotion);
}
