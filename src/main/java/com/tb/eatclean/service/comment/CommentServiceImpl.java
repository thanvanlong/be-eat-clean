package com.tb.eatclean.service.comment;

import java.util.List;

import com.tb.eatclean.dto.PaymentDto;
import com.tb.eatclean.entity.comment.Comment;
import com.tb.eatclean.repo.CommentRepo;
import com.tb.eatclean.repo.UserRepo;
import com.tb.eatclean.service.foods.FoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
  @Autowired
  CommentRepo commentRepo;
  @Override
  public Comment save(Comment comment) {
    return commentRepo.save(comment);
  }
}
