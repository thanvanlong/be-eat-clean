package com.tb.eatclean.repo;

import java.util.List;

import com.tb.eatclean.entity.carts.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Carts, Long> {
  List<Carts> findAllByUserIdAndStatus(Long userId, int status);

  Carts findByUserIdAndBookIdAndStatus(Long userId, Long bookId, int status);
}
