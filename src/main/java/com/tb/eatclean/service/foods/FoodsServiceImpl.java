package com.tb.eatclean.service.foods;

import com.tb.eatclean.entity.Metadata;
import com.tb.eatclean.entity.foods.Foods;
import com.tb.eatclean.repo.FoodsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodsServiceImpl implements FoodsService {
    @Autowired
    private FoodsRepo foodsRepo;

    @Override
    public List<Foods> getAll() {
        return null;
    }

    @Override
    public Map<String, Object> pagingSort(int page, int limit) {
        Pageable pagingSort = PageRequest.of(page, limit);
        Page<Foods> foodsPage = foodsRepo.findAll(pagingSort);

        Metadata metadata = new Metadata();
        metadata.setPageNumber(foodsPage.getNumber());
        metadata.setPageSize(foodsPage.getSize());
        metadata.setTotalPages(foodsPage.getTotalPages());
        metadata.setTotalItems(foodsPage.getTotalElements());

        Map<String, Object> response = new HashMap<>();
        response.put("results", foodsPage.getContent());
        response.put("metadata", metadata);

        return response;
    }

    @Override
    public Map<String, Object> pagingSortSearch(int page, int limit, String search) {
        Pageable pagingSort = PageRequest.of(page, limit);
        Page<Foods> foodsPage = foodsRepo.findAllByNameContaining(search, pagingSort);

        Metadata metadata = new Metadata();
        metadata.setPageNumber(foodsPage.getNumber());
        metadata.setPageSize(foodsPage.getSize());
        metadata.setTotalPages(foodsPage.getTotalPages());
        metadata.setTotalItems(foodsPage.getTotalElements());

        Map<String, Object> response = new HashMap<>();
        response.put("results", foodsPage.getContent());
        response.put("metadata", metadata);


        return response;
    }

    @Override
    public Foods get(Long id) {
        return null;
    }

    @Override
    public Foods getFoodsById(Long id) throws Exception{
        Optional<Foods> foods = foodsRepo.findById(id);
        if(foods.isPresent()) return foods.get();
        throw new Exception("Khong co do an nay");
    }

    @Override
    public String create(Foods foods) throws Exception {
        foodsRepo.save(foods);
        return "Tao san pham thanh cong";
    }

    @Override
    public String update(Long id, Foods foods) {
        return null;
    }

    @Override
    public Object remove(Long id) {
        return null;
    }
}
