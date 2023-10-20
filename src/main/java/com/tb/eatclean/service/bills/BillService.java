package com.tb.eatclean.service.bills;

import com.tb.eatclean.dto.BillDto;
import com.tb.eatclean.entity.bills.Bills;

import java.util.ArrayList;
import java.util.List;

public interface BillService {
  List<Bills> getHistoryByUser(Long userId) throws Exception;
  Bills getBillById(Long id) throws Exception;

  List<Bills> getAllBill() throws Exception;

  String createBill(BillDto billDto) throws Exception;

  Bills getBill(Long id) throws Exception;

  String updateBill(Long id, BillDto updateBillDto) throws Exception;

}
