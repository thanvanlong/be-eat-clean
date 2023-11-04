package com.tb.eatclean.service.bill;

import com.tb.eatclean.dto.BillDto;
import com.tb.eatclean.entity.bill.Bill;
import com.tb.eatclean.entity.user.User;

import java.util.List;

public interface BillService {
    Bill save(Bill bill);

    Bill getById(long id);

    List<Bill> getBillByUser(User user);

}
