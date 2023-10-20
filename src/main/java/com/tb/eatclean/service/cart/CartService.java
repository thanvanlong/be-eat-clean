package com.tb.eatclean.service.cart;

import com.tb.eatclean.entity.carts.Carts;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
  List<Carts> getCartByUser(Long userId) throws Exception;

  String createCartByUser(Carts cart) throws Exception;

  String updateCart(Carts cartUpdate, Long id) throws Exception;

  Object deleteCart(Long id) throws Exception;
}
