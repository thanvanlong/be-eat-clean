package com.tb.eatclean.service.bill;

import com.tb.eatclean.entity.bill.Bill;
import com.tb.eatclean.repo.BillRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepo billRepo;

    @Override
    public Bill save(Bill bill) {
        return billRepo.save(bill);
    }
}
