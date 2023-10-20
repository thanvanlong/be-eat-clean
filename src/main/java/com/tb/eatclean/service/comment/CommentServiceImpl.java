package com.tb.eatclean.service.comment;

import com.tb.eatclean.entity.ResponseDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tb.eatclean.dto.CommentDto;
import com.tb.eatclean.entity.comments.Comments;
import com.tb.eatclean.entity.foods.Foods;
import com.tb.eatclean.entity.user.User;
import com.tb.eatclean.repo.CommentRepo;
import com.tb.eatclean.repo.UserRepo;
import com.tb.eatclean.service.comment.CommentService;
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
    try {
//      ResponseDTO<Books> book = bookService.get(bookId);

//      if (book.getData() == null) {
//        return new ResponseDTO<>("Không tìm thấy Id của sách", null);
//      }

      ArrayList<CommentDto> result = new ArrayList<>();
      commentRepo.findAllByBookId(bookId).stream().map(
          row -> {
            Optional<User> user = userRepo.findById(row.getUser().getId());

            CommentDto comment = new CommentDto();
            comment.setId(row.getId());
//            comment.setBook(book.getData());
            comment.setRate(row.getRate());
            comment.setComment(row.getComment());
            comment.setUser(user.get());

            result.add(comment);
            return row;
          }
      ).collect(Collectors.toList());

      return result;
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public String createComment(Comments comment) throws Exception{
    try {
      Optional<User> user = userRepo.findById(comment.getUser().getId());
      if (!user.isPresent()) {
        throw new Exception("Không tồn tại user này!");
      }

      List<Comments> exists = commentRepo.findAllByFoodsIdAndUserId(
          comment.getFoods().getId(), comment.getUser().getId());

      if (exists.size() > 0) {
        throw new Exception("Bạn đã đánh giá sản phẩm này rồi!");
      }

      Foods foods = foodsService.get(comment.getFoods().getId());
//      if (book == null) {
//        return new ResponseDTO<CommentDto>("Không tồn tại sản phẩm này!", null);
//      }

      CommentDto commentDto = new CommentDto();
      commentDto.setUser(user.get());
      commentDto.setRate(comment.getRate());
      commentDto.setComment(comment.getComment());
      commentDto.setFoods(comment.getFoods());

      commentRepo.save(comment);

      return "Tao danh gia thanh cong";
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }
}
