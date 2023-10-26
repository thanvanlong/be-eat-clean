package com.tb.eatclean.service.comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tb.eatclean.dto.CommentDto;
import com.tb.eatclean.entity.comment.Comment;
import com.tb.eatclean.entity.product.Food;
import com.tb.eatclean.entity.user.User;
import com.tb.eatclean.repo.CommentRepo;
import com.tb.eatclean.repo.UserRepo;
import com.tb.eatclean.service.foods.FoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  @Autowired
  private CommentRepo commentRepo;

  @Autowired
  private UserRepo userRepo;
//
  @Autowired
  private FoodsService foodsService;

  @Override
  public List<CommentDto> getAllCommentOfBook(Long bookId) throws Exception{
//    try {
////      ResponseDTO<Books> book = bookService.get(bookId);
//
////      if (book.getData() == null) {
////        return new ResponseDTO<>("Không tìm thấy Id của sách", null);
////      }
//
//      ArrayList<CommentDto> result = new ArrayList<>();
//      commentRepo.findAllByBookId(bookId).stream().map(
//          row -> {
//            Optional<User> user = userRepo.findById(row.getUser().getId());
//
//            CommentDto comment = new CommentDto();
//            comment.setId(row.getId());
////            comment.setBook(book.getData());
//            comment.setRate(row.getRate());
//            comment.setComment(row.getComment());
//            comment.setUser(user.get());
//
//            result.add(comment);
//            return row;
//          }
//      ).collect(Collectors.toList());
//
//      return result;
//    } catch (Exception e) {
//      throw new Exception(e.getMessage());
//    }

    return null;
  }

  @Override
  public String createComment(Comment comment) throws Exception{

    return null;
  }
}
