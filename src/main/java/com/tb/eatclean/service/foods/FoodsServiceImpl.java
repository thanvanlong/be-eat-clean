package com.tb.eatclean.service.foods;

import com.google.gson.Gson;
import com.tb.eatclean.entity.Metadata;
import com.tb.eatclean.entity.product.Product;
import com.tb.eatclean.repo.FoodRepo;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
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
    public List<Product> getAll() {
        return foodsRepo.findAll();
    }

    @Override
    public Map<String, Object> pagingSort(int page, int limit) {
        Pageable pagingSort = PageRequest.of(page, limit);
        Page<Product> foodsPage = foodsRepo.findAll(pagingSort);

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
        Page<Product> foodsPage = foodsRepo.findAllByNameContaining(search, pagingSort);

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
    public Product get(Long id) throws Exception{
        Optional<Product> foods = foodsRepo.findById(id);

        if(foods.isPresent()) return foods.get();
        throw new Exception("Khong co san pham nay");
    }

    @Override
    public Product getFoodsById(Long id) throws Exception{
        Optional<Product> foods = foodsRepo.findById(id);
        if(foods.isPresent()) {
            Product product = foods.get();

            return foods.get();
        } else {
            return null;
        }
    }


    @Override
    @Transactional
    public String create(Product foods) throws Exception {
        foodsRepo.save(foods);
        return "Tao san pham thanh cong";
    }

    @Override
    public String update(Long id, Product foods) {
        return null;
    }

    @Override
    public Object remove(Long id) {
        return null;
    }

    @Override
    public Product save(Product product) {
        return foodsRepo.save(product);
    }

    @Override
    public Map<String, Object> filter(int page, int limit, String search, String label, String filter, String sortType) {
        Pageable pageable = PageRequest.of(page, limit, new Gson().fromJson(sortType, Sort.Direction.class), filter);
        System.out.println(search + "longtv");
        Page<Product> foodsPage = foodsRepo.findByName(search, label, pageable);
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
