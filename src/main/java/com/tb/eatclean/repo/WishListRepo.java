package com.tb.eatclean.repo;

import com.tb.eatclean.entity.wishlist.WishList;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepo extends JpaRepository<WishList, Long> {
    
}
