package com.tb.eatclean.entity.comment;

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
@Table(name = "comments")
public class Comment  {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String comment;
  private int rate;
  @ManyToOne
  private User user;
  @ManyToOne
  private Food food;
  @CreationTimestamp
  private LocalDateTime createAt;
  @UpdateTimestamp
  private LocalDateTime updateAt;
}
