package com.tb.eatclean.repo;

import java.util.List;

import com.tb.eatclean.entity.comment.Comment;
import com.tb.eatclean.entity.product.Food;
import com.tb.eatclean.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
  List<Comment> findAllByFood(Food food);
}
