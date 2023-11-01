package com.tb.eatclean.entity.carts;

import com.tb.eatclean.entity.product.Product;
import com.tb.eatclean.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart  {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private int quantity;
  @Enumerated
  private Status status;
  @ManyToOne
  private User user;
  @ManyToOne
  private Product foods;
  @CreationTimestamp
  private LocalDateTime createAt;
  @UpdateTimestamp
  private LocalDateTime updateAt;
}
