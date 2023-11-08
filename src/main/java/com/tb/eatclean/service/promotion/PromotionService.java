package com.tb.eatclean.service.promotion;

import com.tb.eatclean.entity.promotion.Promotion;

import java.util.Map;

public interface PromotionService {
    Promotion getByCode(String code);
    Promotion getById(Long id) throws Exception;
    void save(Promotion promotion) throws Exception;
    void delete(Long id) throws Exception;

    Map<String, Object> search(int page, int limit, String search);
}
