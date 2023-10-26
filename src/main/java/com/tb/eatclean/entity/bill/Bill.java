package com.tb.eatclean.entity.bill;

import com.tb.eatclean.entity.carts.Cart;
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
@Table(name = "bills")
public class Bill  {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String date;
  private BillStatus status;
  private Long userId;
  @CreationTimestamp
  private LocalDateTime createAt;
  @UpdateTimestamp
  private LocalDateTime updateAt;

  @Column(name = "list_books",columnDefinition = "json")
  private String listBooks;

  @Column(name = "user_info",columnDefinition = "json")
  private String userInfo;

  @Transient
  @OneToMany
  private List<Cart> listProducts;

  @Transient
  private Map<String, Object> info;
}
