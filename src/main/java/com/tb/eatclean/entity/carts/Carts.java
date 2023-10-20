package com.tb.eatclean.entity.carts;

import com.tb.eatclean.entity.CommonObjectDTO;
import com.tb.eatclean.entity.foods.Foods;
import com.tb.eatclean.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Carts extends CommonObjectDTO {
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
  private Foods foods;
}
