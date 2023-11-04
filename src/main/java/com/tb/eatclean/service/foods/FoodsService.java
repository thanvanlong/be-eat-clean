package com.tb.eatclean.service.foods;

import com.tb.eatclean.entity.product.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface FoodsService {
    List<Product> getAll();

    Map<String, Object> pagingSort(int page, int limit);

    Map<String, Object> pagingSortSearch(int page, int limit, String search);

    Product get(Long id);

    Product getFoodsById(Long id) throws Exception;

    String create(Product foods) throws Exception;

    String update(Long id, Product foods);

    Object remove(Long id);

    Product save(Product product);

    Map<String, Object> filter(int page, int limit, String search, String label, String filter, String sortType);
}
