package com.tb.eatclean.repo;

import com.tb.eatclean.entity.bill.Bill;
import com.tb.eatclean.entity.bill.BillStatus;
import com.tb.eatclean.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BillRepo extends JpaRepository<Bill, Long> {
  List<Bill> findByUser(User user);
  List<Bill> findAllByUserId(Long userId);

  @Query("SELECT MONTH(b.updateAt), SUM(b.price) " +
          "FROM Bill b " +
          "where b.billStatus = 1 AND YEAR(b.updateAt) = :year "+
          "GROUP BY MONTH(b.updateAt)")
  List<Object[]> statsRevenue(int year);

  @Query("SELECT SUM(b.price) " +
          "FROM Bill b " +
          "where b.billStatus = 1 AND YEAR(b.updateAt) = :year AND MONTH(b.updateAt) = :month AND DAY(b.updateAt) = :day "+
          "GROUP BY YEAR(b.updateAt), MONTH(b.updateAt), DAY(b.updateAt)")
  Long statsRevenue(int year, int month, int day);

  @Query("SELECT distinct ca.id, SUM(f.price * c.quantity) " +
          "FROM Bill b " +
          "INNER JOIN b.carts c "+
          "INNER JOIN c.foods f "+
          "INNER JOIN f.categories ca "+
          "where b.billStatus = 1 AND YEAR(b.updateAt) = :year " +
          "GROUP BY ca.id")
  List<Object[]> statsCategory (int year);

  @Query("SELECT distinct ca.id, SUM(b.price), b " +
          "FROM Bill b " +
          "INNER JOIN b.carts c "+
          "INNER JOIN c.foods f "+
          "INNER JOIN f.categories ca "+
          "where b.billStatus = 1 AND YEAR(b.updateAt) = :year AND MONTH(b.updateAt) = :month AND DAY(b.updateAt) = :day " +
          "GROUP BY ca.id")
  List<Object[]> statsCategory (int year, int month, int day);

  @Query("SELECT b.username, ca.label, f.id, f.name, f.price, c.quantity " +
          "FROM Bill b " +
          "INNER JOIN b.carts c "+
          "INNER JOIN c.foods f "+
          "INNER JOIN f.categories ca "+
          "where b.billStatus = 1 AND YEAR(b.updateAt) = :year ")
  List<Object[]> statsEveryDay(int year);

  List<Bill> findByBillStatusAndUpdateAtIsBetween(BillStatus billStatus, LocalDateTime startAt, LocalDateTime endAt);

}
