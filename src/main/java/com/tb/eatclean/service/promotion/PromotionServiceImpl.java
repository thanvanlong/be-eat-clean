package com.tb.eatclean.service.promotion;

import com.tb.eatclean.entity.Metadata;
import com.tb.eatclean.entity.promotion.Promotion;
import com.tb.eatclean.repo.PromotionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PromotionServiceImpl implements PromotionService{
    @Autowired
    PromotionRepo promotionRepo;
    @Override
    public Promotion getByCode(String code) {
        return promotionRepo.findByCode(code);
    }

    @Override
    public void save(Promotion promotion) throws Exception{
        promotionRepo.save(promotion);
    }

    @Override
    public Map<String, Object> search(int page, int limit, String search) {
        Pageable pagingSort = PageRequest.of(page, limit);
        Page<Promotion> promotionPage = promotionRepo.findByNameContaining(search ,pagingSort);

        Metadata metadata = new Metadata();
        metadata.setPageNumber(promotionPage.getNumber());
        metadata.setPageSize(promotionPage.getSize());
        metadata.setTotalPages(promotionPage.getTotalPages());
        metadata.setTotalItems(promotionPage.getTotalElements());

        Map<String, Object> response = new HashMap<>();
        response.put("results", promotionPage.getContent());
        response.put("metadata", metadata);

        return response;
    }
}
