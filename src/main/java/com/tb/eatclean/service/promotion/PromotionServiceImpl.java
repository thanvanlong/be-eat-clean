package com.tb.eatclean.service.promotion;

import com.tb.eatclean.entity.Metadata;
import com.tb.eatclean.entity.promotion.Promotion;
import com.tb.eatclean.repo.PromotionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService{
    @Autowired
    PromotionRepo promotionRepo;
    @Override
    public Promotion getByCode(String code) {
        return promotionRepo.findByCode(code);
    }

    @Override
    public Promotion getById(Long id) throws Exception{
        Optional<Promotion> promotion = promotionRepo.findById(id);
        if(promotion.isPresent()) return promotion.get();
        throw new Exception("Ma giam gia khong ton tai");
    }

    @Override
    public void save(Promotion promotion) throws Exception{
        Promotion promotion1 = promotionRepo.findByCode(promotion.getCode());
        if(promotion.getId() != null) {
            if(promotion1 != null && Objects.equals(promotion1.getId(), promotion.getId())) {
                promotionRepo.save(promotion);
                return;
            }
        }
        if(promotion1 != null) throw new Exception("Ma code da ton tai");
        promotionRepo.save(promotion);
    }

    @Override
    public void delete(Long id) throws Exception {
        Promotion promotion = getById(id);
        promotionRepo.delete(promotion);
    }

    @Override
    public Map<String, Object> search(int page, int limit, String search) {
        Pageable pagingSort = PageRequest.of(page, limit, Sort.Direction.DESC, "updateAt");
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
