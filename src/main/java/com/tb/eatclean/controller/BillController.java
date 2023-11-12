package com.tb.eatclean.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tb.eatclean.dto.BillDto;
import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.entity.bill.Bill;
import com.tb.eatclean.entity.bill.BillStatus;
import com.tb.eatclean.entity.blog.Blog;
import com.tb.eatclean.entity.user.User;
import com.tb.eatclean.repo.BillRepo;
import com.tb.eatclean.service.bill.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @Autowired
    private BillRepo billRepo;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<?>> getOne(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new ResponseDTO<>(billService.getById(id), "200", "", true));
    }


    @GetMapping("/")
    public ResponseEntity<ResponseDTO<?>> getAll() {
        return ResponseEntity.ok(new ResponseDTO<>(billService.getBills(), "200", "", true));
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseDTO<?>> getByUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "16") int limit
    ) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> data = new HashMap<>();
        if (principal instanceof String && !((String) principal).isEmpty()) {
            Pageable pageable = PageRequest.of(page, limit);
            data = billService.getBillByUser((String) principal, pageable);
        }
        return ResponseEntity.ok(new ResponseDTO<>(data, "200", "", true));
    }

    @PutMapping(value = "/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO<?>> updateOne(@RequestParam("id") Long id, @RequestParam("status") BillStatus status) {
        System.out.println(id + " " + status);
        Bill bill = billService.getById(id);
        bill.setBillStatus(status);
        billService.save(bill);
        return ResponseEntity.ok(new ResponseDTO<>("Cap nhat thanh cong" , "200", "Success", true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable("id") Long id) throws Exception{
        Bill bill = billService.getById(id);
        billRepo.delete(bill);
        return ResponseEntity.ok(new ResponseDTO<>("Da xoa thanh cong" , "200", "Success", true));
    }

}
