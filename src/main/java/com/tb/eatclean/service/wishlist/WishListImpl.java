package com.tb.eatclean.service.wishlist;

import com.tb.eatclean.entity.user.User;
import com.tb.eatclean.entity.wishlist.WishList;
import com.tb.eatclean.repo.WishListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class WishListImpl implements WishListService{
    @Autowired
    WishListRepo wishListRepo;
    @Override
    public void save(WishList wishList) {
        wishListRepo.save(wishList);
    }

    @Override
    public Page<WishList> getWishListByUser(User user) {
        return null;
    }
}
