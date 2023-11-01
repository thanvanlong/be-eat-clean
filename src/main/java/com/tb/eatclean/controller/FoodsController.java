package com.tb.eatclean.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.mservice.config.Environment;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import com.mservice.processor.CreateOrderMoMo;
import com.mservice.shared.utils.LogUtils;
import com.tb.eatclean.entity.bill.Bill;
import com.tb.eatclean.entity.bill.BillStatus;
import com.tb.eatclean.entity.carts.Cart;
import com.tb.eatclean.entity.carts.Status;
import com.tb.eatclean.entity.categorie.Categorie;
import com.tb.eatclean.entity.product.Product;
import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.entity.user.User;
import com.tb.eatclean.service.bill.BillService;
import com.tb.eatclean.service.cart.CartService;
import com.tb.eatclean.service.categorie.CategoriesService;
import com.tb.eatclean.service.foods.FoodsService;
import com.tb.eatclean.service.user.UserService;
import com.tb.eatclean.utils.StringUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

//import javax.persistence.EntityManager;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/products")
public class FoodsController {

  @Autowired
  private FoodsService foodsService;

  @Autowired
  private CategoriesService categoriesService;

  @Autowired
  private UserService userService;

  @Autowired
  private CartService cartService;

  @Autowired
  private BillService billService;
  @PostConstruct
  public void init() {
//    Categorie categorie = new Categorie();
//    categorie.setLabel("Loai mot nhe");
//    categorie.setKey(categorie.getLabel());
//
//    categoriesService.save(categorie);
//
//    for (int i = 0; i < 30; i++) {
//      Product product = new Product();
//      product.setName("Thuc an loai mot " + i);
//      product.setCategory(new HashSet<>(categoriesService.getAllCategories()));
//      product.setQuantity(100);
//      product.setPrice(100000);
//      product.setDescription("sdfghjkldfghjkl;");
//      product.setSlug("dfghjkl;fghjkl;/");
//      product.setShortDescription("sdfghjkl;dfghjkl;'");
//      List<String> strings = new ArrayList<>();
//      strings.add("https://kenh14cdn.com/thumb_w/660/2020/7/17/brvn-15950048783381206275371.jpg");
//      strings.add("https://kenh14cdn.com/thumb_w/660/2020/7/17/brvn-15950048783381206275371.jpg");
//      strings.add("https://kenh14cdn.com/thumb_w/660/2020/7/17/brvn-15950048783381206275371.jpg");
//      strings.add("https://kenh14cdn.com/thumb_w/660/2020/7/17/brvn-15950048783381206275371.jpg");
//
//      product.setImgs(strings);
//
//      try {
//        foodsService.create(product);
//      } catch (Exception e) {
//        throw new RuntimeException(e);
//      }
//    }

    LogUtils.init();
    String requestId = String.valueOf(System.currentTimeMillis());
    String orderId = String.valueOf(System.currentTimeMillis());
    long amount = 5000;
    String orderInfo = "Pay With MoMo";
    String returnURL = "momosdk:/";
    String notifyURL = "momosdk:/";

    Environment environment = Environment.selectEnv("dev");
    try {
      PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET, Boolean.TRUE);
      System.out.println(captureWalletMoMoResponse.getQrCodeUrl());
    } catch (Exception e) {

    }
  }

  @GetMapping("/filter")
  public ResponseEntity<ResponseDTO<Map<String, Object>>> filter(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "16") int limit,
          @RequestParam(defaultValue = "name") String filter,
          @RequestParam(defaultValue = "ASC") String sort,
          @RequestParam(defaultValue = "", required = false) String search,
          @RequestParam(defaultValue = "", required = false) String label
          ) {

    return ResponseEntity.ok(new ResponseDTO<>(foodsService.filter(page, limit, search, label, filter, sort), "200", "Success", true ));
  }

  @GetMapping("/get")
  public ResponseEntity<ResponseDTO<Map<String, Object>>> getAll(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int limit
  ) {
    return ResponseEntity.ok(new ResponseDTO<>(foodsService.pagingSort(page, limit), "200", "Success", true));
  }

  @GetMapping("/search")
  public ResponseEntity<ResponseDTO<Map<String, Object>>> searchByName(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int limit,
          @RequestParam(defaultValue = "id,desc") String[] sort,
          @RequestParam("search") String search
  ){
    return ResponseEntity.ok(new ResponseDTO<>(foodsService.pagingSortSearch(page, limit, search), "200", "Success", true));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDTO<Product>> get(@PathVariable("id") Long id) throws Exception{
    return ResponseEntity.ok(new ResponseDTO<>(foodsService.getFoodsById(id), "200", "Success", true));
  }

  @PostMapping()
  public ResponseEntity<ResponseDTO<String>> create(@RequestBody Product foods) throws Exception{
    return ResponseEntity.ok(new ResponseDTO<>(foodsService.create(foods), "200", "Success", true));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ResponseDTO<String>> update(
      @RequestBody Product bookUpdate,
      @PathVariable Long id
  ) {
    return ResponseEntity.ok(new ResponseDTO<>(foodsService.update(id, bookUpdate), "200", "Success", true));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDTO<Object>> delete(@PathVariable("id") Long id) {
    return ResponseEntity.ok(new ResponseDTO<>(foodsService.remove(id), "200", "Success", true));
  }

  @GetMapping("/category")
  public ResponseEntity<ResponseDTO<?>> getCategories() {
    return ResponseEntity.ok(new ResponseDTO<>(categoriesService.getAllCategories(), "200", "Success", true));
  }


  @PostMapping(value = "/add-cart", consumes = {MediaType.ALL_VALUE})
  public ResponseEntity<ResponseDTO<?>> addCart(@RequestBody Product food) {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    System.out.println(principal);
    User user = null;
    if (principal instanceof String && !((String) principal).isEmpty()) {
      user = userService.findByEmail((String) principal);
    }

    if (user != null) {
      Cart cart = cartService.getByFood(food);
      if (cart == null) {
        cart = new Cart();
        cart.setUser(user);
        cart.setFoods(food);
        cart.setStatus(Status.PENDING);
        cart.setQuantity(food.getOrderCount());
        cartService.save(cart);
        return ResponseEntity.ok(new ResponseDTO<>(true, "200", "Success", true));
      } else {
        cart.setQuantity(food.getOrderCount());
        cartService.save(cart);
        return ResponseEntity.ok(new ResponseDTO<>(false, "200", "Success", true));
      }
    } else {
      return ResponseEntity.ok(new ResponseDTO<>("Vui long dang nhap de dat hang", "400", "Fail", false));
    }
  }


  @GetMapping("/get-cart")
  public ResponseEntity<ResponseDTO<?>> getCart() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    System.out.println(principal);
    User user = null;
    if (principal instanceof String && !((String) principal).isEmpty()) {
      user = userService.findByEmail((String) principal);
    }

    if (user != null) {
      List<Cart> list = cartService.getCartByUser(user);
      return ResponseEntity.ok(new ResponseDTO<>(list, "200", "Success", true));
    } else {
      return ResponseEntity.ok(new ResponseDTO<>("Vui long dang nhap", "400", "Fail", false));
    }
  }

  @GetMapping("/count-cart")
  public ResponseEntity<ResponseDTO<?>> countCart() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    System.out.println(principal);
    User user = null;
    if (principal instanceof String && !((String) principal).isEmpty()) {
      user = userService.findByEmail((String) principal);
    }

    if (user != null) {
      int totalOrder = cartService.countCartByUser(user);
      return ResponseEntity.ok(new ResponseDTO<>(totalOrder, "200", "Success", true));
    } else {
      return ResponseEntity.ok(new ResponseDTO<>("Vui long dang nhap", "400", "Fail", false));
    }
  }


  @PostMapping("/order")
  public ResponseEntity<ResponseDTO<?>> order(@RequestBody Bill bill) {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    System.out.println(principal);
    User user = null;
    if (principal instanceof String && !((String) principal).isEmpty()) {
      user = userService.findByEmail((String) principal);
    }

    if (user != null) {
      List<Cart> carts = bill.getCarts();
      for (Cart cart :
              carts) {
        if (cart != null) {
          cart.setStatus(Status.DONE);
          cartService.save(cart);
          Product product = cart.getFoods();
          product.setQuantity(product.getQuantity() - cart.getQuantity());
          foodsService.save(product);
        }
      }

      bill.setBillStatus(BillStatus.PENDING);

      billService.save(bill);


      LogUtils.init();
      String requestId = String.valueOf(System.currentTimeMillis());
      String orderId = String.valueOf(System.currentTimeMillis());
      long amount = bill.getPrice();
      String orderInfo = "Pay With MoMo";
      String returnURL = "momosdk:/";
      String notifyURL = "momosdk:/";

      Environment environment = Environment.selectEnv("dev");
      try {
        PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET, Boolean.TRUE);
        return ResponseEntity.ok(new ResponseDTO<>(captureWalletMoMoResponse.getQrCodeUrl(), "200", "Success", true));
      } catch (Exception e) {
        return ResponseEntity.ok(new ResponseDTO<>("Thanh toan that bai", "400", "Fail", false));
      }

    }

    return null;
  }


  @GetMapping("/update-bill")
  public ResponseEntity<ResponseDTO<?>> updateBill(@RequestParam Map<String, Object> data) {
    System.out.println(data);

    return null;
  }

}
