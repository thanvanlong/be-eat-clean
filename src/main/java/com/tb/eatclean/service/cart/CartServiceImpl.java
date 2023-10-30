package com.tb.eatclean.service.cart;

import java.util.List;
import java.util.Optional;

import com.tb.eatclean.entity.carts.Cart;
import com.tb.eatclean.entity.carts.Status;
import com.tb.eatclean.entity.product.Food;
import com.tb.eatclean.entity.user.User;
import com.tb.eatclean.repo.CartRepo;
import com.tb.eatclean.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
  @Autowired
  private CartRepo cartRepo;

  @Autowired
  private UserRepo userRepo;


    @Override
    public void save(Cart cart) {
        cartRepo.save(cart);
    }

  @Override
  public Cart getByFood(Food food) {
      Optional<Cart> cart = cartRepo.findByFoods(food);
      return cart.orElse(null);
  }

    @Override
    public List<Cart> getCartByUser(User user) {
        return cartRepo.findByUser(user);
    }

    @Override
    public int countCartByUser(User user) {
        return cartRepo.countByUserAndStatus(user, Status.PENDING);
    }
}
