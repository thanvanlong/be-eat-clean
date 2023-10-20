package com.tb.eatclean.controller;

import java.util.ArrayList;
import java.util.List;

import com.tb.eatclean.dto.CommentDto;
import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.entity.comments.Comments;
import com.tb.eatclean.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/comments")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @GetMapping()
  public ResponseEntity<ResponseDTO<List<CommentDto>>> getAllCommentOfBook(
      @RequestParam(name = "foodId") String foodId) throws Exception{
    return ResponseEntity.ok(new ResponseDTO<>(commentService.getAllCommentOfBook(Long.parseLong(foodId)), "", "", true));
  }

  @PostMapping()
  public ResponseEntity<ResponseDTO<String>> createComment(@RequestBody Comments comment) throws Exception {
    return ResponseEntity.ok(new ResponseDTO<>(commentService.createComment(comment), "200", "Success", true));
  }
}
