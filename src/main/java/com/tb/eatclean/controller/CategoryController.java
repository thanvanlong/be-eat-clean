package com.tb.eatclean.controller;

import java.util.List;

import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.entity.categorie.Categorie;
import com.tb.eatclean.service.categorie.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/categories")
public class CategoryController {

  @Autowired
  private CategoriesService categoriesService;

  @GetMapping
  public ResponseEntity<ResponseDTO<List<Categorie>>> getAll() {
    return ResponseEntity.ok(new ResponseDTO<>(categoriesService.getAllCategories(), "200", "Success", true));
  }
}
