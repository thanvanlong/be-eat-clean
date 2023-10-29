package com.tb.eatclean.service.cart;

import com.tb.eatclean.entity.carts.Cart;
import com.tb.eatclean.entity.product.Food;
import com.tb.eatclean.entity.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    void save(Cart cart);

    Cart getByFood(Food food);

    List<Cart> getCartByUser(User user);

    int countCartByUser(User user);
}
