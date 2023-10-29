package com.tb.eatclean.service.foods;

import com.tb.eatclean.entity.product.Food;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface FoodsService {
    List<Food> getAll();

    Map<String, Object> pagingSort(int page, int limit);

    Map<String, Object> pagingSortSearch(int page, int limit, String search);

    Food get(Long id);

    Food getFoodsById(Long id) throws Exception;

    String create(Food foods) throws Exception;

    String update(Long id, Food foods);

    Object remove(Long id);

    Map<String, Object> filter(int page, int limit, String search, String label, String filter, String sortType);
}
