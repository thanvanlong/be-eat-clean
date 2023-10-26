package com.tb.eatclean.entity.carts;

import com.tb.eatclean.entity.product.Food;
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
@Table(name = "carts")
public class Cart  {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Long userId;
  private Long bookId;
  private int quantity;
  private int status;
  @Transient
  @ManyToOne
  private User user;
  @Transient
  @ManyToOne
  private Food foods;

  @CreationTimestamp
  private LocalDateTime createAt;
  @UpdateTimestamp
  private LocalDateTime updateAt;
}
