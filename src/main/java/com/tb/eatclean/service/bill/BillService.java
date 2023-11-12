package com.tb.eatclean.service.bill;

import com.tb.eatclean.dto.BillDto;
import com.tb.eatclean.entity.bill.Bill;
import com.tb.eatclean.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BillService {
    Bill save(Bill bill);

    Bill getById(long id);

    Map<String, Object> getBillByUser(String email, Pageable pageables);

    Map<String, Object> getBills();

}
