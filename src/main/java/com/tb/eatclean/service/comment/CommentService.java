package com.tb.eatclean.service.comment;

import com.tb.eatclean.dto.CommentDto;
import com.tb.eatclean.entity.comments.Comments;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    List<CommentDto> getAllCommentOfBook(Long bookId) throws Exception;

    String createComment(Comments comment) throws Exception;
}
