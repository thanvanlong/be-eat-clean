package com.tb.eatclean.service.categorie;

import com.tb.eatclean.entity.categorie.Categorie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoriesService {
    List<Categorie> getAllCategories();

    void save(Categorie categorie);
}
