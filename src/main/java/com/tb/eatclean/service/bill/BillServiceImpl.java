package com.tb.eatclean.service.bill;

import com.tb.eatclean.entity.Metadata;
import com.tb.eatclean.entity.bill.Bill;
import com.tb.eatclean.entity.product.Product;
import com.tb.eatclean.entity.user.User;
import com.tb.eatclean.repo.BillRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> getBillByUser(String email, Pageable pageable) {
        Page<Bill> foodsPage = billRepo.findByUserEmail(email, pageable);

        Metadata metadata = new Metadata();
        metadata.setPageNumber(foodsPage.getNumber());
        metadata.setPageSize(foodsPage.getSize());
        metadata.setTotalPages(foodsPage.getTotalPages());
        metadata.setTotalItems(foodsPage.getTotalElements());

        Map<String, Object> response = new HashMap<>();
        response.put("results", foodsPage.getContent());
        response.put("metadata", metadata);

        return response;
    }

    @Override
    public Map<String, Object> getBills() {
        Pageable pagingSort = PageRequest.of(0, 100);
        Page<Bill> foodsPage = billRepo.findAll(pagingSort);

        Metadata metadata = new Metadata();
        metadata.setPageNumber(foodsPage.getNumber());
        metadata.setPageSize(foodsPage.getSize());
        metadata.setTotalPages(foodsPage.getTotalPages());
        metadata.setTotalItems(foodsPage.getTotalElements());

        Map<String, Object> response = new HashMap<>();
        response.put("results", foodsPage.getContent());
        response.put("metadata", metadata);

        return response;
    }
}
