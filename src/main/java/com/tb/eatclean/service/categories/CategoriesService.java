package com.tb.eatclean.service.categories;

import com.tb.eatclean.entity.categories.Categories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoriesService {
    List<Categories> getAllCategories();
}
