package com.tb.eatclean.service.bill;

import com.tb.eatclean.entity.bill.Bill;
import com.tb.eatclean.entity.user.User;
import com.tb.eatclean.repo.BillRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillRepo billRepo;

    @Override
    public Bill save(Bill bill) {
        return billRepo.save(bill);
    }

    @Override
    public Bill getById(long id) {
        return billRepo.findById(id).orElse(null);
    }

    @Override
    public List<Bill> getBillByUser(User user) {
        return billRepo.findByUser(user);
    }
}
