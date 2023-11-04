package com.tb.eatclean.service.wishlist;

import com.tb.eatclean.entity.user.User;
import com.tb.eatclean.entity.wishlist.WishList;
import org.springframework.data.domain.Page;

import java.util.List;

public interface WishListService {
    void save(WishList wishList);

    Page<WishList> getWishListByUser(User user);
}
