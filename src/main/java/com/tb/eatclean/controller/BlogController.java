package com.tb.eatclean.controller;

import com.tb.eatclean.dto.AddBlogDto;
import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.entity.blog.Blog;
import com.tb.eatclean.service.blog.BlogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @PostMapping()
    public ResponseEntity<ResponseDTO<String>> save(@Valid @RequestBody AddBlogDto body) throws Exception{
        return ResponseEntity.ok(new ResponseDTO<>(null , "200", "Success", true));
    }
}
