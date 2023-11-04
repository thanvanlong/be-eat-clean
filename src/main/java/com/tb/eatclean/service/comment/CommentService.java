package com.tb.eatclean.service.comment;

import com.tb.eatclean.dto.PaymentDto;
import com.tb.eatclean.entity.comment.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    Comment save(Comment comment);
}
