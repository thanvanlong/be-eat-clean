package com.tb.eatclean.service.bills;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.tb.eatclean.dto.BillDto;
import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.entity.bills.Bills;
import com.tb.eatclean.entity.carts.Carts;
import com.tb.eatclean.entity.user.User;
import com.tb.eatclean.repo.BillRepo;
import com.tb.eatclean.repo.CartRepo;
import com.tb.eatclean.repo.UserRepo;
import com.tb.eatclean.service.cart.CartService;
import com.tb.eatclean.service.foods.FoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements BillService {

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  private BillRepo billRepo;

  @Autowired
  private UserRepo userRepo;

  @Autowired
  private CartRepo cartRepo;

  @Autowired
  private CartService cartService;

  @Autowired
  private FoodsService foodsService;

  @Override
  public List<Bills> getHistoryByUser(Long userId) throws Exception{
    try {
      Optional<User> user = userRepo.findById(userId);

//      if (!user.isPresent()) {
//        return new ResponseDTO<String>("Không tìm thấy người dùng!", "400", "Faild", false);
//      }

      List<Bills> bills = billRepo.findAllByUserId(userId);

      if (bills.size() > 0) {
        for (Bills item: bills) {
          Map<String, Object> listBooks = objectMapper.readValue(item.getListBooks(), Map.class);
          ArrayList<Carts> tmp = new ArrayList<>();
          item.setInfo(objectMapper.readValue(item.getUserInfo(), Map.class));

          ArrayList<Integer> cartIds = (ArrayList<Integer>) listBooks.get("carts");

          for (var cartId: cartIds) {
            tmp.add(this.getCart(cartId.longValue()));
          }
          item.setListProducts(tmp);
        }
      }

      return bills;
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public Bills getBillById(Long id) throws Exception {
    Optional<Bills> bill = billRepo.findById(id);

    if (bill.isPresent()) {
      return bill.get();
    }
    throw new Exception("Hóa đơn không tồn tại!");
  }

  @Override
  public List<Bills> getAllBill() throws Exception {
    try {
      ArrayList<Bills> bills = (ArrayList<Bills>) billRepo.findAll();

      if (bills.size() > 0) {
        for (Bills item: bills) {
          Map<String, Object> listBooks = objectMapper.readValue(item.getListBooks(), Map.class);
          ArrayList<Carts> tmp = new ArrayList<>();
          item.setInfo(objectMapper.readValue(item.getUserInfo(), Map.class));

          ArrayList<Integer> cartIds = (ArrayList<Integer>) listBooks.get("carts");

          for (var cartId: cartIds) {
            tmp.add(this.getCart(cartId.longValue()));
          }
          item.setListProducts(tmp);
        }
      }

      return bills;

    } catch (Exception e) {
      throw  new Exception(e.getMessage());
    }
  }

  @Override
  public String createBill(BillDto billDto) throws Exception{
    try {
      objectMapper = new ObjectMapper();
      Optional<User> user = userRepo.findById(billDto.getUserId());

//      if (!user.isPresent()) {
//        return new ResponseObject<Bills>("Không tìm thấy người dùng!", null);
//      }

      Bills bill = new Bills();
      bill.setDate(billDto.getDate());
      bill.setStatus(billDto.getStatus());
      bill.setUserId(billDto.getUserId());
      bill.setListBooks(objectMapper.writeValueAsString(billDto.getListFoods()));
      bill.setUserInfo(objectMapper.writeValueAsString(billDto.getUserInfo()));

      ArrayList<Integer> cartIds = (ArrayList<Integer>) billDto.getListFoods().get("carts");
      for (Integer cartId: cartIds) {
        Carts c = new Carts();
        c.setStatus(0);
        c.setQuantity(this.getCart(cartId.longValue()).getQuantity());
        cartService.updateCart(c, cartId.longValue());
      }

      return "Thanh toán thành công!";
    } catch (Exception e) {
      throw  new Exception(e.getMessage());
    }
  }

  @Override
  public Bills getBill(Long id) throws Exception{
    try {
      Bills bill = this.getBillById(id);

      Map<String, Object> listBooks = objectMapper.readValue(bill.getListBooks(), Map.class);
      ArrayList<Carts> tmp = new ArrayList<>();
      bill.setInfo(objectMapper.readValue(bill.getUserInfo(), Map.class));

      ArrayList<Integer> cartIds = (ArrayList<Integer>) listBooks.get("carts");

      for (var cartId: cartIds) {
        tmp.add(this.getCart(cartId.longValue()));
      }
      bill.setListProducts(tmp);

      return bill;
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public String updateBill(Long id, BillDto updateBillDto) throws Exception{
    try {
      Optional<Bills> bill = billRepo.findById(id);

      if (!bill.isPresent()) {
        throw new Exception("Hóa đơn không tồn tại!");
      }

      Map<String, Object> listBooks = objectMapper.readValue(bill.get().getListBooks(), Map.class);
      ArrayList<Carts> tmp = new ArrayList<>();
      bill.get().setInfo(objectMapper.readValue(bill.get().getUserInfo(), Map.class));

      ArrayList<Integer> cartIds = (ArrayList<Integer>) listBooks.get("carts");

      for (var cartId: cartIds) {
        tmp.add(this.getCart(cartId.longValue()));
      }
      bill.get().setListProducts(tmp);
      bill.get().setStatus(updateBillDto.getStatus());


      billRepo.save(bill.get());
      return "Them don thanh cong";

    } catch (Exception e) {
      throw  new Exception(e.getMessage());
    }
  }

  public Carts getCart(Long id) {
    try {
      Optional<Carts> cart = cartRepo.findById(id);

      if (!cart.isPresent()) {
        return null;
      }

      Optional<User> user = userRepo.findById(cart.get().getUserId());

//      cart.get().setFoods(foodsService.get(cart.get().getBookId()).getData());
      cart.get().setUser(user.get());

      return cart.get();
    } catch (Exception e) {
      return null;
    }
  }
}
