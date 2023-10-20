package com.tb.eatclean.service.foods;

import com.tb.eatclean.entity.foods.Foods;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface FoodsService {
    List<Foods> getAll();

    Map<String, Object> pagingSort(int page, int limit);

    Map<String, Object> pagingSortSearch(int page, int limit, String search);

    Foods get(Long id);

    Foods getFoodsById(Long id) throws Exception;

    String create(Foods foods) throws Exception;

    String update(Long id, Foods foods);

    Object remove(Long id);
}
