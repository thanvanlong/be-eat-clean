package com.tb.eatclean.repo;

import com.tb.eatclean.entity.bill.Bill;
import com.tb.eatclean.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepo extends JpaRepository<Bill, Long> {
  List<Bill> findByUser(User user);
  List<Bill> findAllByUserId(Long userId);

  @Query("SELECT MONTH(b.createAt), SUM(b.price) " +
          "FROM Bill b " +
          "where b.billStatus = 1 AND YEAR(b.createAt) = :year "+
          "GROUP BY MONTH(b.createAt)")
  List<Object[]> statsRevenue(int year);

  @Query("SELECT ca.id, SUM(b.price) " +
          "FROM Bill b " +
          "INNER JOIN b.carts c "+
          "INNER JOIN c.foods f "+
          "INNER JOIN f.categories ca "+
          "where b.billStatus = 1 AND YEAR(b.createAt) = :year " +
          "GROUP BY ca.id")
  List<Object[]> statsCategory (int year);

  @Query("SELECT ca.id, SUM(b.price) " +
          "FROM Bill b " +
          "INNER JOIN b.carts c "+
          "INNER JOIN c.foods f "+
          "INNER JOIN f.categories ca "+
          "where b.billStatus = 1 AND YEAR(b.createAt) = :year AND MONTH(b.createAt) = :month AND DAY(b.createAt) = :day " +
          "GROUP BY ca.id")
  List<Object[]> statsCategory (int year, int month, int day);

  @Query("SELECT b.username, ca.label, f.id, f.name, f.price, c.quantity " +
          "FROM Bill b " +
          "INNER JOIN b.carts c "+
          "INNER JOIN c.foods f "+
          "INNER JOIN f.categories ca "+
          "where b.billStatus = 1 AND YEAR(b.createAt) = :year ")
  List<Object[]> statsEveryDay(int year);

}
