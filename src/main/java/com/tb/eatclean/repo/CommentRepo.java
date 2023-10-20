package com.tb.eatclean.repo;

import java.util.List;

import com.tb.eatclean.entity.comments.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comments, Long> {
  List<Comments> findAllByFoodsIdAndUserId(Long bookId, Long userId);
  List<Comments> findAllByBookId(Long bookId);
}
