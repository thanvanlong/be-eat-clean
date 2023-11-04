package com.tb.eatclean.repo;

import com.tb.eatclean.entity.bill.Bill;
import com.tb.eatclean.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepo extends JpaRepository<Bill, Long> {
  List<Bill> findByUser(User user);
}
