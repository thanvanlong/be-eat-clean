package com.tb.eatclean.entity.comments;

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
@Table(name = "comments")
public class Comments extends CommonObjectDTO {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String comment;
  private int rate;

  @ManyToOne
  @Transient
  private User user;

  @ManyToOne
  @Transient
  private Foods foods;
}
