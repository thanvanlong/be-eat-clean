package com.tb.eatclean.service.foods;

import com.google.gson.Gson;
import com.tb.eatclean.entity.Metadata;
import com.tb.eatclean.entity.product.Food;
import com.tb.eatclean.repo.FoodRepo;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FoodsServiceImpl implements FoodsService {
    @Autowired
    private FoodRepo foodsRepo;

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Food> getAll() {
        return foodsRepo.findAll();
    }

    @Override
    public Map<String, Object> pagingSort(int page, int limit) {
        Pageable pagingSort = PageRequest.of(page, limit);
        Page<Food> foodsPage = foodsRepo.findAll(pagingSort);

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
        Page<Food> foodsPage = foodsRepo.findAllByNameContaining(search, pagingSort);

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
    public Food get(Long id) {
        return null;
    }

    @Override
    public Food getFoodsById(Long id) throws Exception{
        Optional<Food> foods = foodsRepo.findById(id);
        if(foods.isPresent()) return foods.get();
        throw new Exception("Khong co do an nay");
    }

    @Override
    @Transactional
    public String create(Food foods) throws Exception {
        foodsRepo.save(foods);
        return "Tao san pham thanh cong";
    }

    @Override
    public String update(Long id, Food foods) {
        return null;
    }

    @Override
    public Object remove(Long id) {
        return null;
    }

    @Override
    public Map<String, Object> filter(int page, int limit, String search, String label, String filter, String sortType) {
        Pageable pageable = PageRequest.of(page, limit, new Gson().fromJson(sortType, Sort.Direction.class), filter);
        System.out.println(search + "longtv");
        Page<Food> foodsPage = foodsRepo.findByName(search, label, pageable);
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
}
