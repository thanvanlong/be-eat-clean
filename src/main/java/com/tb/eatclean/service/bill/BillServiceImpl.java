//package com.tb.eatclean.service.bill;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import com.tb.eatclean.dto.BillDto;
//import com.tb.eatclean.entity.bill.Bill;
//import com.tb.eatclean.entity.carts.Cart;
//import com.tb.eatclean.entity.user.User;
//import com.tb.eatclean.repo.BillRepo;
//import com.tb.eatclean.repo.CartRepo;
//import com.tb.eatclean.repo.UserRepo;
////import com.tb.eatclean.service.cart.CartService;
//import com.tb.eatclean.service.foods.FoodsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class BillServiceImpl implements BillService {
//
//  @Autowired
//  ObjectMapper objectMapper;
//
//  @Autowired
//  private BillRepo billRepo;
//
//  @Autowired
//  private UserRepo userRepo;
//
//  @Autowired
//  private CartRepo cartRepo;
//
//  @Autowired
//  private CartService cartService;
//
//  @Autowired
//  private FoodsService foodsService;
//
//  @Override
//  public List<Bill> getHistoryByUser(Long userId) throws Exception{
//
//    return null;
//
//  }
//
//  @Override
//  public Bill getBillById(Long id) throws Exception {
//    Optional<Bill> bill = billRepo.findById(id);
//
//    if (bill.isPresent()) {
//      return bill.get();
//    }
//    throw new Exception("Hóa đơn không tồn tại!");
//  }
//
//  @Override
//  public List<Bill> getAllBill() throws Exception {
//    try {
//      ArrayList<Bill> bills = (ArrayList<Bill>) billRepo.findAll();
//
//      if (bills.size() > 0) {
//        for (Bill item: bills) {
//          Map<String, Object> listBooks = objectMapper.readValue(item.getListBooks(), Map.class);
//          ArrayList<Cart> tmp = new ArrayList<>();
//          item.setInfo(objectMapper.readValue(item.getUserInfo(), Map.class));
//
//          ArrayList<Integer> cartIds = (ArrayList<Integer>) listBooks.get("carts");
//
//          for (var cartId: cartIds) {
//            tmp.add(this.getCart(cartId.longValue()));
//          }
//          item.setListProducts(tmp);
//        }
//      }
//
//      return bills;
//
//    } catch (Exception e) {
//      throw  new Exception(e.getMessage());
//    }
//  }
//
//  @Override
//  public String createBill(BillDto billDto) throws Exception{
//    try {
//      objectMapper = new ObjectMapper();
//      Optional<User> user = userRepo.findById(billDto.getUserId());
//
////      if (!user.isPresent()) {
////        return new ResponseObject<Bills>("Không tìm thấy người dùng!", null);
////      }
//
//      Bill bill = new Bill();
//      bill.setDate(billDto.getDate());
//      bill.setStatus(billDto.getStatus());
//      bill.setUserId(billDto.getUserId());
//      bill.setListBooks(objectMapper.writeValueAsString(billDto.getListFoods()));
//      bill.setUserInfo(objectMapper.writeValueAsString(billDto.getUserInfo()));
//
//      ArrayList<Integer> cartIds = (ArrayList<Integer>) billDto.getListFoods().get("carts");
//      for (Integer cartId: cartIds) {
//        Cart c = new Cart();
//        c.setStatus(0);
//        c.setQuantity(this.getCart(cartId.longValue()).getQuantity());
//        cartService.updateCart(c, cartId.longValue());
//      }
//
//      return "Thanh toán thành công!";
//    } catch (Exception e) {
//      throw  new Exception(e.getMessage());
//    }
//  }
//
//  @Override
//  public Bill getBill(Long id) throws Exception{
//    try {
//      Bill bill = this.getBillById(id);
//
//      Map<String, Object> listBooks = objectMapper.readValue(bill.getListBooks(), Map.class);
//      ArrayList<Cart> tmp = new ArrayList<>();
//      bill.setInfo(objectMapper.readValue(bill.getUserInfo(), Map.class));
//
//      ArrayList<Integer> cartIds = (ArrayList<Integer>) listBooks.get("carts");
//
//      for (var cartId: cartIds) {
//        tmp.add(this.getCart(cartId.longValue()));
//      }
//      bill.setListProducts(tmp);
//
//      return bill;
//    } catch (Exception e) {
//      throw new Exception(e.getMessage());
//    }
//  }
//
//  @Override
//  public String updateBill(Long id, BillDto updateBillDto) throws Exception{
//    try {
//      Optional<Bill> bill = billRepo.findById(id);
//
//      if (!bill.isPresent()) {
//        throw new Exception("Hóa đơn không tồn tại!");
//      }
//
//      Map<String, Object> listBooks = objectMapper.readValue(bill.get().getListBooks(), Map.class);
//      ArrayList<Cart> tmp = new ArrayList<>();
//      bill.get().setInfo(objectMapper.readValue(bill.get().getUserInfo(), Map.class));
//
//      ArrayList<Integer> cartIds = (ArrayList<Integer>) listBooks.get("carts");
//
//      for (var cartId: cartIds) {
//        tmp.add(this.getCart(cartId.longValue()));
//      }
//      bill.get().setListProducts(tmp);
//      bill.get().setStatus(updateBillDto.getStatus());
//
//
//      billRepo.save(bill.get());
//      return "Them don thanh cong";
//
//    } catch (Exception e) {
//      throw  new Exception(e.getMessage());
//    }
//  }
//
//  public Cart getCart(Long id) {
//    return null;
//  }
//}
