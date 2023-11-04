package com.tb.eatclean.repo;

import com.tb.eatclean.entity.categorie.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Categorie, Long> {

    @Query("SELECT c.id, c.label FROM Categorie c")
    List<Object[]> getCategorySelect();
}
