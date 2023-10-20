package com.tb.eatclean.repo;

import com.tb.eatclean.entity.bills.Bills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BillRepo extends JpaRepository<Bills, Long> {
  List<Bills> findAllByUserId(Long userId);
}
