package com.tb.eatclean.repo;

import com.tb.eatclean.entity.promotion.Promotion;
import com.tb.eatclean.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    Page<User> findByNameContaining(String search, Pageable pageable);
}
