package com.tb.eatclean.repo;

import com.tb.eatclean.entity.categories.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Categories, Long> {

}
