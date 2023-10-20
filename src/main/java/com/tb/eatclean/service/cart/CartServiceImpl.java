package com.tb.eatclean.service.cart;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tb.eatclean.entity.carts.Carts;
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

//  @Autowired
//  private BookService bookService;

  @Override
  public List<Carts> getCartByUser(Long userId) throws Exception{
    try {
      Optional<User> user = userRepo.findById(userId);

      if (!user.isPresent()) {
        throw  new Exception("Không tìm thấy người dùng!");
      }

//      List<Carts> carts = cartRepo.findAllByUserIdAndStatus(userId, 1).stream().map(
//          row -> {
//            ResponseObject<Books> book = bookService.get(row.getBookId());
//            row.setUser(user.get());
//            row.setBooks(book.getData());
//            return row;
//          }
//      ).collect(Collectors.toList());

      return null;
    } catch (Exception e) {
      throw  new Exception(e.getMessage());
    }
  }

  @Override
  public String createCartByUser(Carts cart) throws Exception{
    try {
      Optional<User> user = userRepo.findById(cart.getUserId());

      if (!user.isPresent()) {
        throw  new Exception("Không tìm thấy người dùng!");
      }

//      ResponseObject<Books> book = bookService.get(cart.getBookId());

//      if (book.getData() == null) {
//        return new ResponseObject("Không tìm thấy  sản phẩm!", null);
//      }

      Optional<Carts> existsCart = Optional.ofNullable(
          cartRepo.findByUserIdAndBookIdAndStatus(cart.getUserId(),
              cart.getBookId(), 1));

      if (existsCart.isPresent()) {
//        return new ResponseObject("Sản phẩm đã này có trong giỏ hàng!", null);
      }

      Carts newCart = new Carts();
      newCart.setUserId(cart.getUserId());
      newCart.setBookId(cart.getBookId());
      newCart.setUser(user.get());
//      newCart.setBooks(book.getData());
      newCart.setQuantity(cart.getQuantity());
      newCart.setStatus(1);

      newCart = cartRepo.save(newCart);

      return "Them san pham vao gio hang thanh cong";
    } catch (Exception e) {
      throw  new Exception(e.getMessage());
    }
  }

  @Override
  public String updateCart(Carts cartUpdate, Long id) throws Exception{
    try {
      if (cartUpdate.getQuantity() < 1) {
        throw new Exception("Số lượng đặt hàng phải lớn hơn 0!");
      }

      Optional<Carts> cart = cartRepo.findById(id);

      if (!cart.isPresent()) {
        throw  new Exception("Không tồn tại giỏ hàng này!!");
      }

      Optional<User> user = userRepo.findById(cart.get().getUserId());

      cart.get().setQuantity(cartUpdate.getQuantity());
      cart.get().setUser(user.get());
//      cart.get().setBooks(bookService.get(cart.get().getBookId()).getData());
      cart.get().setStatus(cartUpdate.getStatus());

      cartRepo.save(cart.get());
      return "Cap nhat gio hang thanh cong";
    } catch (Exception e) {
      throw new Exception(
          e.getMessage()
      );
    }
  }

  @Override
  public Object deleteCart(Long id) throws Exception{
    try {
      Optional<Carts> cart = cartRepo.findById(id);

      if (!cart.isPresent()) {
        throw new Exception("Không tồn tại giỏ hàng này!!");
      }

      cartRepo.deleteById(cart.get().getId());

      return true;
    } catch (Exception e) {
     throw new Exception(e.getMessage());
    }
  }
}
