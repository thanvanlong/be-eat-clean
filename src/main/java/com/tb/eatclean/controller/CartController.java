package com.tb.eatclean.controller;

import java.util.List;

import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.entity.carts.Cart;
import com.tb.eatclean.entity.product.Food;
import com.tb.eatclean.entity.user.User;
import com.tb.eatclean.service.cart.CartService;
import com.tb.eatclean.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/api/v1/cart")
public class CartController {
  @Autowired
  private CartService cartService;
  @Autowired
  private UserService userService;

  @PostMapping(value = "/add")
  public ResponseEntity<ResponseDTO<?>> addCart() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User user = null;
    if (principal instanceof String && !((String) principal).isEmpty()) {
      user = userService.findByEmail((String) principal);
    }
    System.out.println(principal);

    return ResponseEntity.ok(new ResponseDTO<>("Add success", "200", "Success", true));
  }
}
