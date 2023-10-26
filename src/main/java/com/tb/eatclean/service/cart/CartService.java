package com.tb.eatclean.service.cart;

import com.tb.eatclean.entity.carts.Cart;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
  List<Cart> getCartByUser(Long userId) throws Exception;

  String createCartByUser(Cart cart) throws Exception;

  String updateCart(Cart cartUpdate, Long id) throws Exception;

  Object deleteCart(Long id) throws Exception;
}
