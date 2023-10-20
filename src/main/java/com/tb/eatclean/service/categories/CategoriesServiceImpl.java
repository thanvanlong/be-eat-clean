package com.tb.eatclean.service.categories;

import com.tb.eatclean.entity.categories.Categories;
import com.tb.eatclean.repo.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public List<Categories> getAllCategories() {
        return categoryRepo.findAll();
    }
}
