package com.tb.eatclean.service.bill;

import com.tb.eatclean.dto.BillDto;
import com.tb.eatclean.entity.bill.Bill;

import java.util.List;

public interface BillService {
  List<Bill> getHistoryByUser(Long userId) throws Exception;
  Bill getBillById(Long id) throws Exception;

  List<Bill> getAllBill() throws Exception;

  String createBill(BillDto billDto) throws Exception;

  Bill getBill(Long id) throws Exception;

  String updateBill(Long id, BillDto updateBillDto) throws Exception;

}
