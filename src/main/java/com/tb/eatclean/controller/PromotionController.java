package com.tb.eatclean.controller;

import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.entity.promotion.Promotion;
import com.tb.eatclean.service.promotion.PromotionService;
import com.tb.eatclean.utils.CloudinaryUtils;
import com.tb.eatclean.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/promotion")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;


    @GetMapping("/list")
    public ResponseEntity<ResponseDTO<Map<String, Object>>> listPromotion(@RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int limit,
                                                                          @RequestParam(defaultValue = "id,desc") String[] sort,
                                                                          @RequestParam(required = false, defaultValue = "") String search) {

        Map<String, Object> promotionPage = promotionService.search(page, limit, search);
        return ResponseEntity.ok(new ResponseDTO<>(promotionPage, "200", "Success", true));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseDTO<Promotion>> getOne(@PathVariable("id") Long id) throws Exception{
        return ResponseEntity.ok(new ResponseDTO<>(promotionService.getById(id), "200", "Success", true));
    }

    @PostMapping(value = "/save")
    public ResponseEntity<ResponseDTO<String>> save(@RequestBody Promotion promotion) throws Exception{
        promotionService.save(promotion);
        return ResponseEntity.ok(new ResponseDTO<>("Da them thanh cong", "200", "Success", true));
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ResponseDTO<String>> update(@PathVariable("id") Long id, @RequestBody Promotion promotion) throws Exception {
        promotion.setId(id);
        promotionService.save(promotion);
        return ResponseEntity.ok(new ResponseDTO<>("Da sua thanh cong", "200", "Success", true));
    }

    @PostMapping(value = "/delete/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable("id") Long id) throws Exception{
        promotionService.delete(id);
        return ResponseEntity.ok(new ResponseDTO<>("Da them thanh cong", "200", "Success", true));
    }
}
