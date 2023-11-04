package com.tb.eatclean.repo;

import com.tb.eatclean.entity.categorie.Categorie;
import com.tb.eatclean.entity.product.Product;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FoodRepo extends JpaRepository<Product, Long> {
    Page<Product> findAllByNameContaining(String search, Pageable pageable);

    @Query("SELECT b  FROM Product b" +
            " LEFT JOIN FETCH b.categories c WHERE c.label LIKE %:label% AND b.name LIKE %:name%")
    Page<Product> findByName(@Param("name") String name, @Param("label") String label, Pageable pageable);
}
