package com.tb.eatclean.controller;

import java.util.ArrayList;
import java.util.List;

import com.tb.eatclean.dto.BillDto;
import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.entity.bills.Bills;
import com.tb.eatclean.service.bills.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/bill")
public class BillController {

  @Autowired
  private BillService billService;

  @GetMapping()
  public ResponseEntity<ResponseDTO<List<Bills>>> getHistoryByUser(
      @RequestParam(name = "userId", defaultValue = "null") String userId) throws Exception{
    if (!userId.equals("null")) {
      return ResponseEntity.ok(new ResponseDTO<>(billService.getHistoryByUser(Long.parseLong(userId)), "", "", true));
    } else {
      return ResponseEntity.ok(new ResponseDTO<>(billService.getAllBill(), "200", "Success", true));
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDTO<Bills>> getBill(@PathVariable("id") Long id) throws Exception{
    return ResponseEntity.ok(new ResponseDTO<>(billService.getBill(id), "", "", true));
  }

  @PostMapping()
  public ResponseEntity<ResponseDTO<String>> createBill(@RequestBody BillDto bill) throws Exception{
    return ResponseEntity.ok(new ResponseDTO<>(billService.createBill(bill), "", "", true));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ResponseDTO<String>> updateBill(@PathVariable("id") Long id ,@RequestBody BillDto bill) throws Exception{
    return ResponseEntity.ok(new ResponseDTO<>(billService.updateBill(id, bill), "", "", true));
  }
}
