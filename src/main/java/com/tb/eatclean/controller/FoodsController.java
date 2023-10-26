package com.tb.eatclean.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.tb.eatclean.common.ConvertArrayType;
import com.tb.eatclean.entity.categorie.Categorie;
import com.tb.eatclean.entity.product.Food;
import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.service.categorie.CategoriesService;
import com.tb.eatclean.service.foods.FoodsService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/books")
public class FoodsController {

  @Autowired
  private FoodsService foodsService;

  @Autowired
  private CategoriesService categoriesService;

  public void init() {
    Categorie categorie = new Categorie();
    categorie.setLabel("Hao hang loai 1");
    categoriesService.save(categorie);

    Food food = new Food();
    food.setName("Thuc an loai mot");
    food.setCategory(new HashSet<>(categoriesService.getAllCategories()));
    food.setQuantity(100);
    food.setPrice(100000);
    food.setDescription("sdfghjkldfghjkl;");
    food.setSlug("dfghjkl;fghjkl;/");
    food.setShortDescription("sdfghjkl;dfghjkl;'");
    List<String> strings = new ArrayList<>();
    strings.add("https://kenh14cdn.com/thumb_w/660/2020/7/17/brvn-15950048783381206275371.jpg");
    strings.add("https://kenh14cdn.com/thumb_w/660/2020/7/17/brvn-15950048783381206275371.jpg");
    strings.add("https://kenh14cdn.com/thumb_w/660/2020/7/17/brvn-15950048783381206275371.jpg");
    strings.add("https://kenh14cdn.com/thumb_w/660/2020/7/17/brvn-15950048783381206275371.jpg");

    food.setImgs(ConvertArrayType.convertToString(strings));

    try {
      foodsService.create(food);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @GetMapping("/")
  public ResponseEntity<ResponseDTO<Map<String, Object>>> getAll(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int limit
  ) {
    return ResponseEntity.ok(new ResponseDTO<>(foodsService.pagingSort(page, limit), "200", "Success", true));
  }

  @GetMapping("/search")
  public ResponseEntity<ResponseDTO<Map<String, Object>>> searchByName(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int limit,
          @RequestParam(defaultValue = "id,desc") String[] sort,
          @RequestParam("search") String search
  ){
    return ResponseEntity.ok(new ResponseDTO<>(foodsService.pagingSortSearch(page, limit, search), "200", "Success", true));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDTO<Food>> get(@PathVariable("id") Long id) throws Exception{
    return ResponseEntity.ok(new ResponseDTO<>(foodsService.getFoodsById(id), "200", "Success", true));
  }

  @PostMapping()
  public ResponseEntity<ResponseDTO<String>> create(@RequestBody Food foods) throws Exception{
    return ResponseEntity.ok(new ResponseDTO<>(foodsService.create(foods), "200", "Success", true));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResponseDTO<String>> update(
      @RequestBody Food bookUpdate,
      @PathVariable Long id
  ) {
    return ResponseEntity.ok(new ResponseDTO<>(foodsService.update(id, bookUpdate), "200", "Success", true));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDTO<Object>> delete(@PathVariable("id") Long id) {
    return ResponseEntity.ok(new ResponseDTO<>(foodsService.remove(id), "200", "Success", true));
  }
}
