package com.tb.eatclean.entity.bill;

import com.tb.eatclean.entity.carts.Cart;
import com.tb.eatclean.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bill")
public class Bill  {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @ManyToOne
  private User user;
  @OneToMany
  private List<Cart> carts;
  @Enumerated
  private MethodType methodType = MethodType.MOMO;
  @Enumerated
  private BillStatus billStatus;

  @CreationTimestamp
  private LocalDateTime createAt;
  @UpdateTimestamp
  private LocalDateTime updateAt;
  private String username;
  private String address;
  private String phone;
  private String note;
  private long price;
}
