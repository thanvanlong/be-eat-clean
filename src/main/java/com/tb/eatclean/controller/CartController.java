package com.tb.eatclean.controller;

import java.util.List;

import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.entity.carts.Cart;
import com.tb.eatclean.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/cart")
public class CartController {
  @Autowired
  private CartService cartService;

  @GetMapping()
  public ResponseEntity<ResponseDTO<List<Cart>>> getCartByUser(
      @RequestParam( name = "userId") String userId) throws Exception{
    return ResponseEntity.ok(new ResponseDTO<>(cartService.getCartByUser(Long.parseLong(userId)), "200", "Success", true));
  }

  @PostMapping()
  public ResponseEntity<ResponseDTO<String>> createCartByUser(@RequestBody Cart cart) throws Exception{
    return ResponseEntity.ok(new ResponseDTO<>(cartService.createCartByUser(cart), "200", "Success", true));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ResponseDTO<String>> updateCartItem(
          @RequestBody Cart cartUpdate, @PathVariable("id") Long id
  ) throws Exception {
    return ResponseEntity.ok(new ResponseDTO<>(cartService.updateCart(cartUpdate, id), "200", "Success", true));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDTO<Object>> deleteCartItem(@PathVariable("id") Long id) throws Exception{
    return ResponseEntity.ok(new ResponseDTO<>(cartService.deleteCart(id), "", "Success", true));
  }
}
