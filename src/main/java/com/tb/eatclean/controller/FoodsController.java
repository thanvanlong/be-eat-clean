package com.tb.eatclean.controller;

import java.util.List;
import java.util.Map;

import com.tb.eatclean.entity.foods.Foods;
import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.service.foods.FoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/books")
public class FoodsController {

  @Autowired
  private FoodsService foodsService;

  @GetMapping()
  public ResponseEntity<ResponseDTO<Map<String, Object>>> getAll(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int limit,
          @RequestParam(defaultValue = "id,desc") String[] sort,
          @RequestParam(required = false) String search
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
  public ResponseEntity<ResponseDTO<Foods>> get(@PathVariable("id") Long id) throws Exception{
    return ResponseEntity.ok(new ResponseDTO<>(foodsService.getFoodsById(id), "200", "Success", true));
  }

  @PostMapping()
  public ResponseEntity<ResponseDTO<String>> create(@RequestBody Foods foods) throws Exception{
    return ResponseEntity.ok(new ResponseDTO<>(foodsService.create(foods), "200", "Success", true));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResponseDTO<String>> update(
      @RequestBody Foods bookUpdate,
      @PathVariable Long id
  ) {
    return ResponseEntity.ok(new ResponseDTO<>(foodsService.update(id, bookUpdate), "200", "Success", true));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDTO<Object>> delete(@PathVariable("id") Long id) {
    return ResponseEntity.ok(new ResponseDTO<>(foodsService.remove(id), "200", "Success", true));
  }
}
