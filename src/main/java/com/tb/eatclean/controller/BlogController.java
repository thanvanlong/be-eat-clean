package com.tb.eatclean.controller;

import com.tb.eatclean.dto.AddBlogDto;
import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.entity.blog.Blog;
import com.tb.eatclean.entity.blog.BlogState;
import com.tb.eatclean.repo.BlogRepo;
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

    @Autowired
    private BlogRepo blogRepo;

    @PostMapping()
    public ResponseEntity<ResponseDTO<String>> save(@RequestBody AddBlogDto body) throws Exception{
        Blog blog = new Blog();

        blog.setDescription(body.getDescription());
        blog.setTitle(body.getTitle());
        blog.setContent(body.getContent());
        blog.setImgThumbnail(body.getSetImgThumbnail());

        blogService.save(blog);

        return ResponseEntity.ok(new ResponseDTO<>("Da them thanh cong" , "200", "Success", true));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> update(@PathVariable("id") Long id, @RequestBody AddBlogDto body) throws Exception{
        Blog blog = blogService.getById(id);
        blog.setDescription(body.getDescription());
        blog.setTitle(body.getTitle());
        blog.setContent(body.getContent());
        blog.setImgThumbnail(body.getSetImgThumbnail());
        blog.setBlogState(body.getBlogState());

        blogService.save(blog);

        return ResponseEntity.ok(new ResponseDTO<>("Cap nhat thanh cong" , "200", "Success", true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable("id") Long id) throws Exception{
        Blog blog = blogService.getById(id);
        blog.setBlogState(BlogState.Deleted);

        blogService.save(blog);

        return ResponseEntity.ok(new ResponseDTO<>("Da xoa thanh cong" , "200", "Success", true));
    }
}
