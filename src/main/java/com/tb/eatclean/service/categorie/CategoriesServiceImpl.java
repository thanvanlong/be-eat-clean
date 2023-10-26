package com.tb.eatclean.service.categorie;

import com.tb.eatclean.entity.categorie.Categorie;
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
    public List<Categorie> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public void save(Categorie categorie) {
        categoryRepo.save(categorie);
    }
}
