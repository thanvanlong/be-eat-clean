package com.tb.eatclean.repo;

import com.tb.eatclean.entity.categorie.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Categorie, Long> {

}
