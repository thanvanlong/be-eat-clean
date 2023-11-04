package com.tb.eatclean.service.promotion;

import com.tb.eatclean.entity.promotion.Promotion;

import java.util.Map;

public interface PromotionService {
    Promotion getByCode(String code);
    void save(Promotion promotion) throws Exception;

    Map<String, Object> search(int page, int limit, String search);
}
