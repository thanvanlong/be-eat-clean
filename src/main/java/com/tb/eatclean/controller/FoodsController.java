package com.tb.eatclean.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import com.google.gson.Gson;
import com.mservice.config.Environment;
import com.mservice.enums.RequestType;
import com.mservice.models.PaymentResponse;
import com.mservice.processor.CreateOrderMoMo;
import com.mservice.shared.utils.LogUtils;
import com.tb.eatclean.dto.PaymentDto;
import com.tb.eatclean.entity.bill.Bill;
import com.tb.eatclean.entity.bill.BillStatus;
import com.tb.eatclean.entity.bill.MethodType;
import com.tb.eatclean.entity.blog.Blog;
import com.tb.eatclean.entity.carts.Cart;
import com.tb.eatclean.entity.carts.Status;
import com.tb.eatclean.entity.categorie.Categorie;
import com.tb.eatclean.entity.comment.Comment;
import com.tb.eatclean.entity.product.Product;
import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.entity.promotion.Promotion;
import com.tb.eatclean.entity.user.User;
import com.tb.eatclean.repo.FoodRepo;
import com.tb.eatclean.service.bill.BillService;
import com.tb.eatclean.service.blog.BlogService;
import com.tb.eatclean.service.cart.CartService;
import com.tb.eatclean.service.categorie.CategoriesService;
import com.tb.eatclean.service.comment.CommentService;
import com.tb.eatclean.service.foods.FoodsService;
import com.tb.eatclean.service.promotion.PromotionService;
import com.tb.eatclean.service.user.UserService;
import com.tb.eatclean.utils.CloudinaryUtils;
import com.tb.eatclean.utils.StringUtils;
import io.swagger.annotations.ApiParam;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

//import javax.persistence.EntityManager;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/products")
public class FoodsController {

  @Autowired
  private FoodsService foodsService;

  @Autowired
  private FoodRepo foodRepo;

  @Autowired
  private CategoriesService categoriesService;

  @Autowired
  private UserService userService;

  @Autowired
  private CartService cartService;

  @Autowired
  private BillService billService;
  @Autowired
  private PromotionService promotionService;
  @Autowired
  private CommentService commentService;
  @Autowired
  private BlogService blogService;

  // create-running-destroy

  @PostConstruct
  public void init() {
    Categorie categorie = new Categorie();
    categorie.setLabel("Loai mot nhe");
    categorie.setKey(categorie.getLabel());

    categoriesService.save(categorie);

    for (int i = 0; i < 4; i++) {
      Product product = new Product();
      product.setName("Thuc an loai mot " + i + 1);
      Set<Long> longs = new HashSet<>();
      longs.add(1L);
      product.setCategories(longs);
      product.setQuantity(100);
      product.setPrice(100000);
      product.setDescription("sdfghjkldfghjkl;");
      product.setSlug("dfghjkl;fghjkl;/");
      product.setShortDescription("sdfghjkl;dfghjkl;'");
      List<String> strings = new ArrayList<>();
      strings.add("https://kenh14cdn.com/thumb_w/660/2020/7/17/brvn-15950048783381206275371.jpg");
      strings.add("https://kenh14cdn.com/thumb_w/660/2020/7/17/brvn-15950048783381206275371.jpg");
      strings.add("https://kenh14cdn.com/thumb_w/660/2020/7/17/brvn-15950048783381206275371.jpg");
      strings.add("https://kenh14cdn.com/thumb_w/660/2020/7/17/brvn-15950048783381206275371.jpg");

      product.setImgs(strings);

      try {
        foodsService.create(product);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

//    LogUtils.init();
//    String requestId = String.valueOf(System.currentTimeMillis());
//    String orderId = String.valueOf(System.currentTimeMillis());
//    long amount = 5000;
//    String orderInfo = "Pay With MoMo";
//    String returnURL = "";
//    String notifyURL = "https://webhook.site/1307aba7-41e6-403f-8739-31a91303a549";
//
//    Environment environment = Environment.selectEnv("dev");
//    try {
//      PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET, Boolean.TRUE);
//      System.out.println(captureWalletMoMoResponse.getQrCodeUrl());
//    } catch (Exception e) {
//
//    }

//    for (int i = 0; i < 10; i++) {
//      Blog blog = new Blog();
//      blog.setDescription("Ăn sáng là bước quan trọng trong việc duy trì một lối sống lành mạnh và cân bằng dinh dưỡng. Trong thế giới ẩm thực ngày nay, nền tảng ăn kiêng keto đã thu hút sự quan tâm từ rất nhiều bạn trẻ.");
//      blog.setTitle("Thử Ngay 10+ Thực Đơn Bữa Sáng Keto Cho Các Cô Nàng Muốn Giảm Cân, Giữ Dáng");
//      blog.setContent("<p>Ăn sáng là bước quan trọng trong việc duy trì một lối sống lành mạnh và cân bằng dinh dưỡng. Trong thế giới ẩm thực ngày nay, nền tảng ăn kiêng keto đã thu hút sự quan tâm từ rất nhiều bạn trẻ. Với nguồn gốc từ chế độ ăn ít carbohydrate và giàu chất béo,&nbsp;<em>ăn sáng keto&nbsp;</em>đã trở thành một lựa chọn phổ biến cho những người muốn giảm cân và cải thiện sức khỏe. Cùng&nbsp;<a href=\"https://healthyeating.shop/\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(0, 128, 0);\"><strong>Healthy Eating</strong></a>&nbsp;theo dõi bài viết dưới đây để biết thêm nhiều công thức nấu&nbsp;<a href=\"https://healthyeating.shop/blog-giam-can/bua-sang-keto/\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(0, 128, 0);\"><strong>bữa sáng Keto</strong></a>&nbsp;ngon và healthy cho gia đình bạn nhé!</p><p>&nbsp;</p><p class=\"ql-indent-6\"><img src=\"https://healthyeating.shop/wp-content/uploads/2023/06/bua-sang-keto-700x375.jpg\"></p><p>&nbsp;</p><h2><strong style=\"color: rgb(0, 128, 0);\">Một số lợi ích tuyệt vời của bữa sáng Keto mà bạn cần biết</strong></h2><p>&nbsp;</p><p>Ăn sáng keto không chỉ đơn thuần là một chế độ ăn kiêng, mà còn là giúp hỗ trợ cải thiện sức khỏe. Thay vì dựa vào các loại tinh bột như bánh mì, ngũ cốc, hoặc đường, chế độ ăn sáng keto tập trung vào các nguồn protein như trứng, thịt, cá và các nguồn chất béo lành mạnh.</p><p>&nbsp;</p><p>Để hiểu hơn về&nbsp;<a href=\"https://healthyeating.shop/blog-giam-can/che-do-an-keto-la-gi/\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(0, 128, 0);\"><strong>chế độ Keto là gì</strong></a>? Dưới đây là một số lợi ích của&nbsp;<strong><em>ăn sáng theo chế độ keto</em></strong>:</p><p>&nbsp;</p><p><br></p><ul><li><strong><em>Giảm cân:</em></strong>&nbsp;Chế độ ăn sáng keto giúp đốt cháy mỡ thừa và giảm cân một cách hiệu quả.</li><li><strong><em>Kiểm soát đường huyết:</em></strong>&nbsp;<strong><em>Ăn sáng keto</em></strong>&nbsp;giúp kiểm soát mức đường huyết và giảm sự biến động đường huyết.</li><li><strong><em>Tăng cường năng lượng:</em></strong>&nbsp;Chế độ ăn sáng keto tạo ra một nguồn năng lượng ổn định và kéo dài.</li><li><strong><em>Cải thiện tập trung:</em></strong>&nbsp;Việc tiêu thụ chất béo lành mạnh và ít carbohydrate trong bữa sáng keto có thể cải thiện tập trung và sự tinh thần.</li><li><strong><em>Giảm cơn thèm ăn:&nbsp;</em></strong>Ăn sáng keto thường tạo cảm giác no lâu hơn và giảm cơn đói vào buổi trưa.</li><li><strong><em>Cải thiện sức khỏe tim mạch:</em></strong>&nbsp;Chế độ ăn sáng keto có thể giúp giảm triglyceride, tăng hàm lượng cholesterol HDL (cholesterol tốt) và giảm hàm lượng cholesterol LDL (cholesterol xấu).</li></ul><p>&nbsp;</p><p><img src=\"https://healthyeating.shop/wp-content/uploads/2023/06/bua-sang-keto-1-655x394.jpg\"></p><p>&nbsp;</p><p><strong style=\"color: rgb(255, 102, 0);\">&gt;&gt;&gt; Xem thêm:</strong><strong>&nbsp;</strong><a href=\"https://healthyeating.shop/blog-giam-can/thuc-don-keto/\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(0, 128, 0);\"><strong>Thực đơn Keto 28 ngày</strong></a><strong>&nbsp;Giảm Cân Hiệu Quả Nhất</strong></p><p>&nbsp;</p><h2><strong style=\"color: rgb(0, 128, 0);\">Các nguyên tắc cơ bản của bữa sáng Keto</strong></h2><p>&nbsp;</p><p>Để có một&nbsp;<em>bữa sáng keto</em>&nbsp;phù hợp với sức khỏe và thành phần dinh dưỡng, mọi người cần tuân thủ một số nguyên tắc cơ bản:</p><ul><li><strong><em>Giới hạn carbohydrate:</em></strong>&nbsp;Chế độ ăn sáng keto yêu cầu giảm lượng carbohydrate, thường dưới 50g mỗi ngày. Hạn chế các nguồn carbohydrate&nbsp;<em>(đường, bột mì, ngũ cốc và các sản phẩm từ sữa,…).</em>&nbsp;Thay vào đó, tập trung vào việc tiêu thụ rau xanh giàu chất xơ và ít carbohydrate&nbsp;<em>(rau cải, bông cải xanh, rau muống, rau diếp cá,…).</em></li><li><strong><em>Tăng cường chất béo và protein:</em></strong>&nbsp;Chế độ ăn sáng keto đòi hỏi một lượng chất béo và protein đáng kể. Bạn hãy đảm bảo lựa chọn các nguồn chất béo lành mạnh và nguồn protein chất lượng (<em>thịt, cá, trứng, hạt chia, hạt hướng dương, dầu ô liu, bơ, kem, phô mai, và các loại hạt,…).</em></li><li><strong><em>Theo dõi lượng calo:</em></strong>&nbsp;Mặc dù chế độ ăn sáng keto không hạn chế calo quá chặt chẽ, nhưng bạn vẫn cần chú ý đến lượng calo tiêu thụ. Để đạt được mục tiêu giảm cân, bạn cần duy trì thặng dư calo nhỏ hoặc calo cân đối, tùy thuộc vào mục tiêu cá nhân của mình.</li><li><strong><em>Bổ sung vitamin và khoáng chất:</em></strong>&nbsp;Vì chế độ ăn sáng keto có thể hạn chế một số nguồn dinh dưỡng, đặc biệt là vitamin và khoáng chất. Do đó, bạn hãy đảm bảo bổ sung đầy đủ các loại vitamin và khoáng chất thông qua thực phẩm hoặc thực phẩm bổ sung.</li><li><strong><em>Uống đủ nước:</em></strong>&nbsp;Khi thực hiện chế độ ăn sáng keto, cần duy trì lượng nước đủ mỗi ngày.</li></ul><p>&nbsp;</p><p><img src=\"https://healthyeating.shop/wp-content/uploads/2023/06/bua-sang-keto-2-592x394.jpg\"></p><p>&nbsp;</p><p><strong style=\"color: rgb(255, 102, 0);\">&gt;&gt;&gt; Có thể bạn quan tâm:</strong><strong>&nbsp;</strong><a href=\"https://healthyeating.shop/blog-giam-can/cach-su-dung-que-thu-keto/\" rel=\"noopener noreferrer\" target=\"_blank\" style=\"color: rgb(0, 128, 0);\"><strong>Cách Sử Dụng Que Thử Keto</strong></a><strong>&nbsp;Nhanh Chóng Và Chuẩn Nhất</strong></p><p>&nbsp;</p><h2><strong style=\"color: rgb(0, 128, 0);\">10+&nbsp;<em>Thực đơn keto bữa sáng&nbsp;</em>dành cho những cô nàng bận rộn</strong></h2><p>&nbsp;</p><p>Với chế độ&nbsp;<em>ăn sáng keto</em>, bạn có rất nhiều sự lựa chọn để thưởng thức, chẳng hạn như:</p><ol><li><strong>Trứng chiên với bơ:&nbsp;</strong>Trứng là một nguồn protein chất lượng cao và chứa ít carbohydrate. Khi kết hợp với bơ giàu chất béo, món ăn này cung cấp năng lượng và giúp bạn cảm thấy no lâu hơn.</li><li><strong><em>Bánh mì nướng ngũ cốc:</em></strong>&nbsp;Bánh mì nguyên hạt nướng, ăn kèm kem hạnh nhân và mứt dứa không đường.</li><li><strong>Salad trứng cải:&nbsp;</strong>Salad trứng cải là một món ăn keto bổ dưỡng và ngon miệng. Bạn có thể chế biến nhanh chóng bằng cách trộn trứng luộc, cải xoong và gia vị thích hợp.</li><li><strong>Sữa chua chất béo với hạt chia và quả mọng:&nbsp;</strong>Sữa chua chất béo có hàm lượng carbohydrate thấp và cung cấp protein và chất xơ. Thêm hạt chia và quả mọng tươi mát sẽ tạo ra một bữa sáng ngon lành và bổ dưỡng.</li><li><strong>Bánh&nbsp;mì&nbsp;keto nướng:&nbsp;</strong>Nướng một ổ bánh mì keto từ bột hạt lanh và bột mì hạt lựu. Thoa một lớp dầu dừa hoặc bơ lên bề mặt và ăn kèm với thịt nguội và hành lá.</li><li><strong><em>Smoothie Bowl:</em></strong>&nbsp;Chia hạt ngâm nước dừa hoặc sữa hạnh nhân, kèm topping như hạt hướng dương, hạt lanh, quả mọng và mứt không đường.</li><li><strong><em>Bánh&nbsp;mì&nbsp;nướng bơ chuối:</em></strong>&nbsp;Bánh mì keto từ bột hạt óc chó, bơ, trứng và chuối, nướng giòn và ăn kèm hạt hướng dương và dứa tươi.</li><li><strong><em>Smoothie trái cây</em></strong>: Hỗn hợp nước hoa quả tươi với các loại trái cây như dứa, kiwi, dâu tây và quả mọng.</li><li><strong><em>Bát trái cây tươi:</em></strong>&nbsp;Kết hợp các loại trái cây như dứa, xoài, kiwi, dâu tây, lựu, cam và nho. Thêm một ít hạt chia hoặc hạt hướng dương để tăng cường chất xơ.</li><li><strong><em>Bánh pancake ngũ cốc:</em></strong>&nbsp;Pancake từ bột ngũ cốc không đường, ăn kèm nước mật ong hoặc siro ngọt tự nhiên và trái cây tươi.</li><li><strong><em>Omelette rau:</em></strong>&nbsp;Trứng khuấy với rau xanh như cải xoong, bông cải xanh, hành lá và cà chua. Ướp gia vị nhẹ và nướng chín, ăn kèm với bánh mì đen.</li><li><strong><em>Bát mì sợi chay:</em></strong>&nbsp;Mì sợi chay từ ngũ cốc hoặc đậu, ăn kèm rau xanh, hành lá, hành tây và nước mắm chay.</li><li><strong><em>Cháo yến mạch:</em></strong>&nbsp;Cháo yến mạch với hạt chia, topping hạt hướng dương và quả mọng tráng miệng.</li><li><strong><em>Bánh mì sandwich:</em></strong>&nbsp;Bánh mì sandwich ngũ cốc với thịt gà, rau sống và muối tiêu.</li><li><strong><em>Bún chả cá:</em></strong>&nbsp;Chả cá chiên giòn kèm rau xanh và sốt dầu ô liu.</li><li><strong><em>Hủ tiếu:</em></strong>&nbsp;Bát hủ tiếu gà hoặc bò xay với rau sống và nước dùng từ xương đậm vị.</li></ol><p>&nbsp;</p><p><img src=\"https://healthyeating.shop/wp-content/uploads/2023/06/bua-sang-keto-3-700x394.jpg\"></p><p>&nbsp;</p><h2><strong style=\"color: rgb(0, 128, 0);\">Lời kết</strong></h2><p>&nbsp;</p><p>Thực đơn&nbsp;<strong style=\"color: rgb(0, 128, 0);\">bữa sáng keto</strong>&nbsp;là một lựa chọn tuyệt vời cho những người muốn duy trì chế độ ăn ít carbohydrate và tập trung vào chất béo và protein. Tuy nhiên, khi thực hiện chế độ ăn sáng keto, bạn cần chú ý cân nhắc và điều chỉnh khẩu phần phù hợp với nhu cầu cá nhân. Hi vọng với sự đa dạng và phong phú của thực đơn mà chúng tôi cung cấp, bạn có thể tận hưởng những món ăn ngon miệng và có thể cải thiện sức khỏe và kiểm soát cân nặng của mình nhé.</p><p><br></p>");
//      blog.setImgThumbnail("https://healthyeating.shop/wp-content/uploads/2023/05/thuc-don-eat-clean-cho-me-cho-con-bu-5-700x394.jpg");
//      blogService.save(blog);
//    }

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

  @PostMapping(value = "/create-product",  produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<ResponseDTO<String>> createProduct(@RequestParam("name") String name,
                                                      @RequestParam(value = "price", defaultValue = "10000") long price,
                                                      @RequestParam(value = "quantity", defaultValue = "10") int quantity,
                                                      @RequestParam(value = "slug", defaultValue = "") String slug,
                                                      @RequestParam(value = "shortDescription", defaultValue = "") String shortDescription,
                                                      @RequestParam(value = "description", required = false, defaultValue = "") String description,
                                                      @RequestParam(value = "categories", required = false) Set<Long> categories,

                                                      @RequestParam("files") MultipartFile[] files) {

    String []typeImg = {"image/png", "image/jpeg", "image/jpg"};
    for (MultipartFile file : files){
      if (!Arrays.asList(typeImg).contains(file.getContentType())) {
        return ResponseEntity.ok(new ResponseDTO<String>("Thể loại của ảnh không hợp lệ", "404", "Failed", false));
      }
    }
//
    Product product = new Product();
    product.setName(name);
    product.setPrice(price);
    product.setQuantity(quantity);
    product.setSlug(slug);
    product.setDescription(description);
    product.setShortDescription(shortDescription);

    if(categories != null)
      product.setCategories(categories);

    List<String> imgUrls = new ArrayList<>();
//
    try {
      for (MultipartFile file : files){
        if (!Arrays.asList(typeImg).contains(file.getContentType())) {
          return ResponseEntity.ok(new ResponseDTO<String>("Thể loại của ảnh không hợp lệ", "404", "Failed", false));
        }
        imgUrls.add(CloudinaryUtils.uploadImg(file.getBytes(), StringUtils.uuidFileName("long")));
      }
      product.setImgs(imgUrls);
      System.out.println(imgUrls);
    } catch (IOException e) {
      return ResponseEntity.ok(new ResponseDTO<String>("Upload ảnh lên không thành công", "404", "Failed", false));
    }
//
//
    foodRepo.save(product);
    return ResponseEntity.ok(new ResponseDTO<>("Tao product thanh cong!", "200", "Success", true));
  }

  @PutMapping(value = "/update-product", produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<ResponseDTO<String>> updateProduct(
          @RequestParam(value = "id") Long id,
          @RequestParam("name") String name,
          @RequestParam(value = "price", required = false) Long price,
          @RequestParam(value = "quantity", required = false) Integer quantity,
          @RequestParam(value = "slug", required = false) String slug,
          @RequestParam(value = "shortDescription", required = false) String shortDescription,
          @RequestParam(value = "description", required = false) String description,
          @RequestParam(value = "categories", required = false) Set<Long> categories,
          @RequestParam(value = "imgs", required = false) List<String> imgs,
          @RequestParam(value = "files", required = false)
          @ApiParam(value = "Danh sách tệp ảnh", required = false, type = "string", format = "binary", allowMultiple = true)
          MultipartFile[] files) throws Exception{

    Product product = foodsService.get(id);

    if(name != null) product.setName(name);
    if(description != null) product.setDescription(description);
    if(categories != null) product.setCategories(categories);
    if(price != null) product.setPrice(price);
    if(shortDescription != null) product.setShortDescription(shortDescription);
    if(slug != null) product.setSlug(slug);
    if(quantity != null) product.setQuantity(quantity);

    List<String> imgUrls = new ArrayList<>();
    if(imgs != null) {
      imgUrls.addAll(imgs);
    }

    if (files != null) {
      String []typeImg = {"image/png", "image/jpeg", "image/jpg"};
      for (MultipartFile file : files){
        if (!Arrays.asList(typeImg).contains(file.getContentType())) {
          return ResponseEntity.ok(new ResponseDTO<String>("Thể loại của ảnh không hợp lệ", "404", "Failed", false));
        }
      }

      try {
        for (MultipartFile file : files){
          if (!Arrays.asList(typeImg).contains(file.getContentType())) {
            return ResponseEntity.ok(new ResponseDTO<String>("Thể loại của ảnh không hợp lệ", "404", "Failed", false));
          }
          imgUrls.add(CloudinaryUtils.uploadImg(file.getBytes(), StringUtils.uuidFileName(name)));
        }

      } catch (IOException e) {
        return ResponseEntity.ok(new ResponseDTO<String>("Upload ảnh lên không thành công", "404", "Failed", false));
      }
    }
    product.setImgs(imgUrls);

    foodRepo.save(product);

    return ResponseEntity.ok(new ResponseDTO<>("Update Room Done!", "200", "Success", true));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDTO<Product>> get(@PathVariable("id") Long id) throws Exception {
    Product product = foodsService.getFoodsById(id);
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    System.out.println(principal);
    User user = null;
    if (principal instanceof String && !((String) principal).isEmpty()) {
      user = userService.findByEmail((String) principal);
      if (user != null) {
        List<Bill> bills = (List<Bill>) billService.getBillByUser(user.getEmail(), PageRequest.of(0, 50)).get("results");
        if (bills == null) {
          bills = new ArrayList<>();
        }
        for (Bill bill :
                bills) {
          if (bill.getBillStatus() == BillStatus.COMPLETED) {
            for (Cart cart :
                    bill.getCarts()) {
              if (product.getId().equals(cart.getFoods().getId())) {
                User finalUser = user;
                if (!product.getComments().stream().anyMatch(it -> (it.getUser() != null && it.getUser().getId().equals(finalUser.getId())))) {
                  product.setCanComment(true);
                }
              }
            }
          }
        }
      }
    }
    return ResponseEntity.ok(new ResponseDTO<>(product, "200", "Success", true));
  }

  @GetMapping("/related/{id}")
  public ResponseEntity<ResponseDTO<Product>> getRelated(@PathVariable("id") Long id) throws Exception{
    return ResponseEntity.ok(new ResponseDTO<>(foodsService.getFoodsById(id), "200", "Success", true));
  }

  @DeleteMapping("/delete-product/{id}")
  public ResponseEntity<ResponseDTO<String>> delete(@PathVariable("id") Long id) throws Exception{
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
        cart.setQuantity(food.getOrderCount() + cart.getQuantity());
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
      return ResponseEntity.ok(new ResponseDTO<>(0, "400", "Fail", true));
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
          try {
            if (cart.getId() < 0) {
              cart.setId(System.currentTimeMillis());
            }
            cartService.save(cart);
          } catch (Exception e) {

          }
          Product product = cart.getFoods();
          product.setQuantity(product.getQuantity() - cart.getQuantity());
          foodsService.save(product);
        }
      }

      bill.setBillStatus(BillStatus.PENDING);
      bill.setUser(user);
      bill.setId(System.currentTimeMillis());
      Promotion promotion = bill.getPromotion();
      try {
        if (promotion != null && promotion.getCode() != null) {
          promotion.setQuantity(promotion.getQuantity() - 1);
          promotionService.save(promotion);
          bill.setDiscount(promotion.getDiscount());
        }
      } catch (Exception e) {

      }
      Bill b = billService.save(bill);

      if (bill.getMethodType() == MethodType.MOMO) {
        LogUtils.init();
        String requestId = String.valueOf(System.currentTimeMillis());
        String orderId = b.getId() + "";
        long amount = bill.getPrice();
        String orderInfo = "Pay With MoMo";
        String returnURL = "http://localhost:5173";
        String notifyURL = "https://webhook.site/1307aba7-41e6-403f-8739-31a91303a549";

        Environment environment = Environment.selectEnv("dev");
        try {
          PaymentResponse captureWalletMoMoResponse = CreateOrderMoMo.process(environment, orderId, requestId, Long.toString(amount), orderInfo, returnURL, notifyURL, "", RequestType.CAPTURE_WALLET, Boolean.TRUE);
          return ResponseEntity.ok(new ResponseDTO<>(captureWalletMoMoResponse.getPayUrl(), "200", "Success", true));
        } catch (Exception e) {
          return ResponseEntity.ok(new ResponseDTO<>("Thanh toan that bai", "400", "Fail", false));
        }
      } else {
        return ResponseEntity.ok(new ResponseDTO<>("Thanh toán thành công", "200", "Success", true));
      }
    }

    return null;
  }

  @PostMapping("/update-bill")
  public void updateBill(@RequestBody String data, HttpServletResponse response) {
    PaymentDto paymentDto = new Gson().fromJson(data, PaymentDto.class);
    if (paymentDto.getResultCode() == 0) {
      Bill bill = billService.getById(Long.parseLong(paymentDto.getOrderId()));
      bill.setBillStatus(BillStatus.COMPLETED);
      billService.save(bill);

      try {
        response.sendRedirect("http://localhost:5173/");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    } else {

    }
  }

  @GetMapping("/promotion")
  public ResponseEntity<ResponseDTO<?>> checkPromotion(@RequestParam String code) {
    Promotion promotion = promotionService.getByCode(code);
    if (promotion != null) {
      return ResponseEntity.ok(new ResponseDTO<>(promotion, "", "", true));
    } else {
      return ResponseEntity.ok(new ResponseDTO<>("Error", "", "", false));
    }
  }


  @PostMapping("/comment")
  public ResponseEntity<ResponseDTO<?>> comment(@RequestBody Comment comment) {
    System.out.println(comment.getComment());
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    System.out.println(principal);
    User user = null;
    if (principal instanceof String && !((String) principal).isEmpty()) {
      user = userService.findByEmail((String) principal);
      comment.setUser(user);
    }

    Comment comment1 =  commentService.save(comment);
    Product product = comment1.getFood();
    product.getComments().add(comment1);
    foodsService.save(product);
    comment1.setFood(null);
    return ResponseEntity.ok(new ResponseDTO<>(comment1, "", "", true));
  }


  @GetMapping("/get-blog")
  public ResponseEntity<ResponseDTO<Map<String, Object>>> getBlogs(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") int limit
  ) {
    return ResponseEntity.ok(new ResponseDTO<>(blogService.pagingSort(page, limit), "200", "Success", true));
  }

  @GetMapping("/get-blog/{id}")
  public ResponseEntity<ResponseDTO<?>> getBlogById(@PathVariable("id") long id) throws Exception{
    Blog blog = blogService.getById(id);
    return ResponseEntity.ok(new ResponseDTO<>(blog, "200", "Success", true));
  }

  @DeleteMapping("/delete-cart")
  public ResponseEntity<ResponseDTO<?>> deleteCart(@RequestParam long id) {
    Cart cart = cartService.getById(id);
    cartService.delete(cart);
    return ResponseEntity.ok(new ResponseDTO<>(true, "", "", true));
  }

  @DeleteMapping("/delete-product")
  public ResponseEntity<ResponseDTO<?>> deleteProduct(@RequestParam long id) throws Exception{
    return ResponseEntity.ok(new ResponseDTO<>(this.foodsService.remove(id), "200", "Success", true));
  }

}
