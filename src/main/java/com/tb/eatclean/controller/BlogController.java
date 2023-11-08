package com.tb.eatclean.controller;

import com.tb.eatclean.dto.AddBlogDto;
import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.entity.blog.Blog;
import com.tb.eatclean.entity.blog.BlogState;
import com.tb.eatclean.repo.BlogRepo;
import com.tb.eatclean.service.blog.BlogService;
import com.tb.eatclean.utils.CloudinaryUtils;
import com.tb.eatclean.utils.StringUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogRepo blogRepo;

    @PostMapping(value = "/create",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO<String>> save(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "content") String content,
            @RequestParam(value = "description", defaultValue = "", required = false) String description,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) throws Exception{
        Blog blog = new Blog();

        blog.setDescription(description);
        blog.setTitle(title);
        blog.setContent(content);
        blog.setImgThumbnail(CloudinaryUtils.uploadImg(file.getBytes(), StringUtils.uuidFileName(title)));
        blog.setBlogState(BlogState.Active);

        blogService.save(blog);
        return ResponseEntity.ok(new ResponseDTO<>("Da them thanh cong" , "200", "Success", true));
    }

    @PutMapping(value = "/update", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDTO<String>> update(
            @RequestParam("id") Long id,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "description", defaultValue = "", required = false) String description,
            @RequestParam(value = "file", required = false) MultipartFile file) throws Exception{
        Blog blog = blogService.getById(id);
        blog.setDescription(description);
        blog.setTitle(title);
        blog.setContent(content);
        if(file != null) blog.setImgThumbnail(CloudinaryUtils.uploadImg(file.getBytes(), StringUtils.uuidFileName(title)));

        blogService.save(blog);
        return ResponseEntity.ok(new ResponseDTO<>("Cap nhat thanh cong" , "200", "Success", true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<String>> delete(@PathVariable("id") Long id) throws Exception{
        Blog blog = blogService.getById(id);
        blogRepo.delete(blog);
        return ResponseEntity.ok(new ResponseDTO<>("Da xoa thanh cong" , "200", "Success", true));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<?>> get(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(new ResponseDTO<>(blogService.getById(id) , "200", "Success", true));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseDTO<>("Khong tim thay blog" , "200", "Success", false));
        }
    }
}
