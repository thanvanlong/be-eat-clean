package com.tb.eatclean.dto;

import com.tb.eatclean.entity.foods.Foods;
import com.tb.eatclean.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
  private Long id;
  private User user;
  private String comment;
  private String date;
  private int rate;
  private Foods foods;
}
