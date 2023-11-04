package com.tb.eatclean.service.promotion;

import com.tb.eatclean.entity.promotion.Promotion;
import com.tb.eatclean.repo.PromotionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImpl implements PromotionService{
    @Autowired
    PromotionRepo promotionRepo;
    @Override
    public Promotion getByCode(String code) {
        return promotionRepo.findByCode(code);
    }

    @Override
    public void save(Promotion promotion) {
        promotionRepo.save(promotion);
    }
}
